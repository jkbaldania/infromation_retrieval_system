package com.v1.irs.batch;

import com.v1.irs.irhandler.InformationRetrievalHandler;
import com.v1.irs.jwt.JwtTokenUtil;
import com.v1.irs.user.User;
import com.v1.irs.user.UserRepository;
import com.v1.irs.zip.ZipManager;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
public class BatchController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserRepository userRepository;

    private AmazonClient amazonClient;

    @Autowired
    private ZipManager zipManager;

    @Autowired
    private InformationRetrievalHandler informationRetrievalHandler;

    @Autowired
    BatchController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @RequestMapping(value = "/delete_account", method = RequestMethod.DELETE)
    public String deleteAccount(@RequestBody User user) throws Exception {

        List<Batch> batches = batchService.findBatchesByUserName(user.getUsername());
        batches.forEach(batch -> {

            try {
                String batchIndexLoc = batch.getBatchIndexLocation();
                String filename2 = batchIndexLoc.substring(batchIndexLoc.lastIndexOf('/') + 1);
//                System.out.println("=====================================");
//                System.out.println(filename2);
//                System.out.println("=====================================");
                amazonClient.deleteFile(filename2);
            } catch (Exception e) {
            }

            try {
                String batchLoc = batch.getBatchLocation();
                String filename1 = batchLoc.substring(batchLoc.lastIndexOf('/') + 1);
//                System.out.println("=====================================");
//                System.out.println(filename1);
//                System.out.println("=====================================");
                amazonClient.deleteFile(filename1);
            } catch (Exception e) {
            }

            batchRepository.delete(batch);
        });
        userRepository.delete(user);

        return "Account of username " + user.getUsername() +" Deleted!";
    }

    @RequestMapping(value = "/add_batch", method = RequestMethod.POST)
    public Batch saveUser(@RequestParam String batchName, @RequestPart(value = "file") MultipartFile file,
                          @RequestHeader (name="Authorization") String token) throws Exception {

        File folder1 = new File("temp_zip");
        folder1.mkdir();
        File folder2 = new File("temp_unzip");
        folder2.mkdir();
        File folder3 = new File("temp_index");
        folder3.mkdir();

        token = token.replace("Bearer ", "");
        Batch batch = new Batch();
        batch.setBatchName(batchName);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        batch.setUserName(userName);
        String s3Loc = this.amazonClient.getFileUrl();

        Integer batchId = batchService.saveBatch(batch);
        String fileName = batchId + "-" + batchName + "-" + userName + ".zip";
        String batchLocation = s3Loc + fileName;
        File convFile = this.amazonClient.uploadFile(file, fileName);

        String unzipLoc = "temp_unzip" + File.separator + fileName.replace(".zip", "");
        List<File> filesInZip = zipManager.unzipFile(convFile, unzipLoc);
        String indexLoc = "temp_index" + File.separator + fileName.replace(".zip", "");
        informationRetrievalHandler.createIndex(filesInZip, indexLoc);

        String zipLoc = "temp_zip" + File.separator + "index-" + fileName;
        File folderToZip = new File(indexLoc);
        zipManager.zipDirectory(folderToZip, zipLoc);

        File zippedIndex = new File(zipLoc);
        this.amazonClient.uploadFile(zippedIndex, zippedIndex.getName());
        String batchIndexLocation = s3Loc + zippedIndex.getName();

        batch.setBatchIndexLocation(batchIndexLocation);
        batch.setBatchLocation(batchLocation);
        batchService.saveBatch(batch);

        convFile.delete();
        FileUtils.cleanDirectory(new File("temp_index"));
        FileUtils.cleanDirectory(new File("temp_unzip"));
        FileUtils.cleanDirectory(new File("temp_zip"));

        return batch;
    }

}
