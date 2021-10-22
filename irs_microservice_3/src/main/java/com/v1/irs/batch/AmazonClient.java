package com.v1.irs.batch;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getFileUrl() {
        return endpointUrl + "/" + bucketName + "/";
    }

    public File uploadFile(MultipartFile multipartFile, String fileName) {
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            uploadFileTos3bucket(fileName, file);
//            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    public void uploadFile(File file, String fileName) {
        try {
            uploadFileTos3bucket(fileName, file);
//            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ByteArrayOutputStream downloadFileFroms3bucket(String fileName) throws IOException {
        S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, fileName));
        InputStream is = s3object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[4096];

        while ((len = is.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        return outputStream;
    }

    public void downloadFile(String fileName) {
        try {
            ByteArrayOutputStream outputStream = downloadFileFroms3bucket(fileName);
            try(OutputStream outputStream2 = new FileOutputStream("temp_index" + "\\" + fileName)) {
                outputStream.writeTo(outputStream2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
