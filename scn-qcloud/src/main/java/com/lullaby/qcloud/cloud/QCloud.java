package com.lullaby.qcloud.cloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Component
public class QCloud {

    @Autowired
    private COSClient cosClient;

    @Value("${cos.bucket_name}")
    private String bucketName;
    @Value("${cos.domain")
    private String domain;

    public void upload(File file) throws FileNotFoundException {

        InputStream inputStream = new FileInputStream(file);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setServerSideEncryption("AES256");
        objectMetadata.setContentLength(file.length());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getName(), inputStream, objectMetadata);

        // 上传腾讯cos存储
        cosClient.putObject(putObjectRequest);
    }

    public void download(String fileName) {
        String url = domain + "/" + fileName;
    }
}
