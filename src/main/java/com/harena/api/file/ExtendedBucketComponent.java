package com.harena.api.file;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.List;

@Component
public class ExtendedBucketComponent {
    private final BucketComponent bucketComponent;
    private final S3Client s3Client;

    public ExtendedBucketComponent(BucketComponent bucketComponent, BucketConf bucketConf) {
        this.bucketComponent = bucketComponent;
        this.s3Client = bucketConf.getS3Client();
    }

    //TODO: implement offset to get to next/previous page
    public List<File> getFilesFromS3(int limit, int offset){
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketComponent.getBucketName())
                .maxKeys(limit)
                .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        List<S3Object> contents = listObjectsV2Response.contents();
        return contents.stream().map(object -> bucketComponent.download(object.key())).toList();
    }
}
