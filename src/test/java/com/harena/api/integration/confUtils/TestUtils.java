package com.harena.api.integration.confUtils;

import static org.mockito.Mockito.when;

import com.harena.api.endpoint.rest.client.ApiClient;
import com.harena.api.file.ExtendedBucketComponent;
import java.io.File;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

public class TestUtils {
  public static ApiClient anApiClient(int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    client.setPort(serverPort);
    return client;
  }

  @SneakyThrows
  private static File getPatrimoineTestFile() {
    return new ClassPathResource("files/patrimoineIloAu13mai24").getFile();
  }

  public static void setupExtendedBucketComponent(ExtendedBucketComponent bucketComponent) {
    when(bucketComponent.getFilesFromS3(1, 0)).thenReturn(List.of(getPatrimoineTestFile()));

    when(bucketComponent.getFileFromS3("patrimoineIloAu13mai24"))
        .thenReturn(Optional.of(getPatrimoineTestFile()));
  }
}
