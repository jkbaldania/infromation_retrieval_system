package com.v1.irs.batch;

import com.v1.irs.irhandler.InformationRetrievalHandler;
import com.v1.irs.jwt.JwtTokenUtil;
import com.v1.irs.zip.ZipManager;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
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

    private AmazonClient amazonClient;

    @Autowired
    private ZipManager zipManager;

    @Autowired
    private InformationRetrievalHandler informationRetrievalHandler;

    @Autowired
    BatchController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @RequestMapping(value = "/query_batch", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public String query(@RequestBody String inputJson,
                          @RequestHeader (name="Authorization") String token) throws Exception {

        File folder1 = new File("temp_zip");
        folder1.mkdir();
        File folder2 = new File("temp_unzip");
        folder2.mkdir();
        File folder3 = new File("temp_index");
        folder3.mkdir();

        JSONObject jsonObj = new JSONObject(inputJson);
        String batchName = jsonObj.getString("batchName");
        String query = jsonObj.getString("query");
        token = token.replace("Bearer ", "");
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        List<Batch> batches = batchRepository.findByUserNameAndBatchName(userName, batchName);
        Integer batchId = batches.get(0).getBatchId();
        String fileName = "index-" + batchId + "-" + batchName + "-" + userName + ".zip";
        this.amazonClient.downloadFile(fileName);

        File zippedIndex = new File("temp_index" + File.separator + fileName);
        String unzipLoc = "temp_unzip" + File.separator + fileName.replace(".zip", "");
        List<File> filesInZip = zipManager.unzipFile(zippedIndex, unzipLoc);
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        unzipLoc = unzipLoc + File.separator + fileName.replace("index-","").replace(".zip","");

        List<JSONObject> listOfObjs = new LinkedList<JSONObject>();
        listOfObjs = informationRetrievalHandler.query(query, unzipLoc);

        FileUtils.cleanDirectory(new File("temp_index"));
        FileUtils.cleanDirectory(new File("temp_unzip"));
        FileUtils.cleanDirectory(new File("temp_zip"));

        return listOfObjs.toString();
    }

}
