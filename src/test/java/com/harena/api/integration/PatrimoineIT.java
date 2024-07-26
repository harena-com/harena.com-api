package com.harena.api.integration;

import static com.harena.api.integration.confUtils.TestMocks.*;
import static com.harena.api.integration.confUtils.TestUtils.setupExtendedBucketComponent;
import static org.junit.Assert.assertEquals;

import com.harena.api.conf.FacadeIT;
import com.harena.api.endpoint.rest.api.PatrimoineApi;
import com.harena.api.endpoint.rest.client.ApiClient;
import com.harena.api.endpoint.rest.client.ApiException;
import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.integration.confUtils.TestUtils;
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
  void setup() {
    setupExtendedBucketComponent(bucketComponent);
  }

  @Test
  void get_paginated_patrimoines_ok() throws ApiException {
    ApiClient apiClient = anApiClient();
    PatrimoineApi api = new PatrimoineApi(apiClient);
    List<Patrimoine> actual = api.getPatrimoines(0, 1).getData();

    assertEquals(List.of(patrimoine_ilo()), actual);
  }
}
