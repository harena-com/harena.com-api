package com.harena.api.integration;

import static com.harena.api.integration.confUtils.TestMocks.*;
import static com.harena.api.integration.confUtils.TestUtils.setupExtendedBucketComponent;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.harena.api.conf.FacadeIT;
import com.harena.api.endpoint.rest.api.PatrimoineApi;
import com.harena.api.endpoint.rest.client.ApiClient;
import com.harena.api.endpoint.rest.client.ApiException;
import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.integration.confUtils.TestUtils;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@AutoConfigureMockMvc
public class PatrimoineIT extends FacadeIT {
  @MockBean ExtendedBucketComponent bucketComponent;
  @LocalServerPort private int port;

  private ApiClient anApiClient() {
    return TestUtils.anApiClient(port);
  }

  @BeforeEach
  void setup() throws URISyntaxException {
    setupExtendedBucketComponent(bucketComponent);
    URL resource = getClass().getClassLoader().getResource("files/patrimoineIloAu13mai24");
    if (resource != null) {
      File testFile = Paths.get(resource.toURI()).toFile();
      when(bucketComponent.getFileFromS3("patrimoineIloAu13mai24")).thenReturn(testFile);
    } else {
      throw new RuntimeException("Test file not found in resources/files");
    }
  }

  @Test
  void get_paginated_patrimoines_ok() throws ApiException {
    ApiClient apiClient = anApiClient();
    PatrimoineApi api = new PatrimoineApi(apiClient);
    List<Patrimoine> actual = api.getPatrimoines(0, 1).getData();

    assertEquals(List.of(patrimoine_ilo()), actual);
  }

  @Test
  void get_patrimoine_by_name_ok() throws ApiException {
    ApiClient apiClient = anApiClient();
    PatrimoineApi api = new PatrimoineApi(apiClient);
    Patrimoine actual = api.getPatrimoineByNom("patrimoineIloAu13mai24");

    assertEquals(patrimoine_ilo(), actual);
  }
}
