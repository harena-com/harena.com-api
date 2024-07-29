package com.harena.api.integration;

import static com.harena.api.integration.confUtils.TestMocks.*;
import static com.harena.api.integration.confUtils.TestUtils.setupExtendedBucketComponent;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.harena.api.conf.FacadeIT;
import com.harena.api.endpoint.rest.api.PossessionApi;
import com.harena.api.endpoint.rest.client.ApiClient;
import com.harena.api.endpoint.rest.client.ApiException;
import com.harena.api.endpoint.rest.model.PossessionAvecType;
import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.integration.confUtils.TestUtils;
import java.util.List;
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
    List<PossessionAvecType> data =
        api.getPatrimoinePossessions(patrimoine_ilo().getNom(), 0, Integer.MAX_VALUE).getData();

    assertTrue(data.contains(argent_espece_de_ilo()));
    assertTrue(data.contains(vie_courant_de_ilo()));
  }
}
