package com.example.__unis_fest_back.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private String baseUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com" ;

    public ArrayList<String> getAllImageUrls() {
        ArrayList<String> imageUrls = new ArrayList<>();

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucket);

        ListObjectsV2Result result = amazonS3.listObjectsV2(request);
        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
            String key = objectSummary.getKey();
            if (key.matches(".*\\.(jpg|jpeg|png|webp|gif)$")) {
                imageUrls.add(baseUrl + "/" + key);
            }
        }

        return imageUrls;
    }
}
