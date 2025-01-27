package com.harena.api.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

@Component
@Slf4j
public class ExtendedBucketComponent {
  private final BucketComponent bucketComponent;
  private final S3Client s3Client;

  public ExtendedBucketComponent(BucketComponent bucketComponent, BucketConf bucketConf) {
    this.bucketComponent = bucketComponent;
    this.s3Client = bucketConf.getS3Client();
  }

  public List<File> getFilesFromS3(int limit, int offset) {
    List<File> results = new ArrayList<>();
    ListObjectsV2Request listObjectsV2Request =
        ListObjectsV2Request.builder()
            .bucket(bucketComponent.getBucketName())
            .maxKeys(limit)
            .build();
    ListObjectsV2Iterable listObjectsV2Iterable =
        s3Client.listObjectsV2Paginator(listObjectsV2Request);
    int currentPage = 0;

    for (ListObjectsV2Response page : listObjectsV2Iterable) {
      currentPage++;
      if (currentPage == offset + 1) {
        page.contents().stream()
            .forEach(
                object -> {
                  results.add(bucketComponent.download(object.key()));
                });
      }
    }

    return results;
  }

  public Optional<File> getFileFromS3(String fileName) {
    try {
      return Optional.of(bucketComponent.download(fileName));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public FileHash upload(File file, String bucketKey) {
    return bucketComponent.upload(file, bucketKey);
  }

  public void deleteFile(String fileName) {
    DeleteObjectRequest deleteObjectRequest =
        DeleteObjectRequest.builder().bucket(bucketComponent.getBucketName()).key(fileName).build();

    s3Client.deleteObject(deleteObjectRequest);
  }
}
