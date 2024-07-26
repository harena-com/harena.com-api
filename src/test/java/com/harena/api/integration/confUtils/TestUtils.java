package com.harena.api.integration.confUtils;

import static org.mockito.Mockito.when;

import com.harena.api.endpoint.rest.client.ApiClient;
import com.harena.api.file.ExtendedBucketComponent;
import java.util.List;
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
  public static void setupExtendedBucketComponent(ExtendedBucketComponent bucketComponent) {
    when(bucketComponent.getFilesFromS3(1, 0))
        .thenReturn(List.of(new ClassPathResource("files/patrimoineIloAu13mai24").getFile()));
  }
}
