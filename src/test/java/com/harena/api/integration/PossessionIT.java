package com.harena.api.integration;

import static com.harena.api.integration.confUtils.TestMocks.patrimoine_ilo;
import static com.harena.api.integration.confUtils.TestUtils.setupExtendedBucketComponent;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.harena.api.conf.FacadeIT;
import com.harena.api.endpoint.rest.api.PossessionApi;
import com.harena.api.endpoint.rest.client.ApiClient;
import com.harena.api.endpoint.rest.client.ApiException;
import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.integration.confUtils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@AutoConfigureMockMvc
public class PossessionIT extends FacadeIT {
  @MockBean ExtendedBucketComponent bucketComponent;
  @LocalServerPort private int port;

  private ApiClient anApiClient() {
    return TestUtils.anApiClient(port);
  }

  @BeforeEach
  void setup() {
    setupExtendedBucketComponent(bucketComponent);
  }

  @Test
  void get_paginated_possession_ok() throws ApiException {
    ApiClient apiClient = anApiClient();
    PossessionApi api = new PossessionApi(apiClient);

    assertEquals(2, api.getPatrimoinePossessions(patrimoine_ilo().getNom(), 0, 1).getData().size());
  }
}
