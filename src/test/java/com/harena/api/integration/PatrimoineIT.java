package com.harena.api.integration;

import static com.harena.api.integration.confUtils.TestMocks.*;
import static com.harena.api.integration.confUtils.TestUtils.setupExtendedBucketComponent;
import static org.junit.jupiter.api.Assertions.*;

import com.harena.api.conf.FacadeIT;
import com.harena.api.endpoint.rest.api.PatrimoineApi;
import com.harena.api.endpoint.rest.client.ApiClient;
import com.harena.api.endpoint.rest.client.ApiException;
import com.harena.api.endpoint.rest.model.GetPatrimoines200Response;
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

  @Test
  void get_patrimoine_by_name_ok() throws ApiException {
    ApiClient apiClient = anApiClient();
    PatrimoineApi api = new PatrimoineApi(apiClient);
    Patrimoine actual = api.getPatrimoineByNom("patrimoineIloAu13mai24");

    assertEquals(patrimoine_ilo(), actual);
  }

  @Test
  void get_patrimoine_by_name_ko() {
    ApiClient apiClient = anApiClient();
    PatrimoineApi api = new PatrimoineApi(apiClient);

    ApiException apiException =
        assertThrows(ApiException.class, () -> api.getPatrimoineByNom("dummy"));
    String responseBody = apiException.getResponseBody();
    assertEquals(
        "{"
            + "\"type\":\"400 BAD_REQUEST\","
            + "\"message\":\""
            + "Patrimoine identified with name dummy not found"
            + "\"}",
        responseBody);
  }

  @Test
  void crupdate_patrimoine_ok() throws ApiException {
    ApiClient apiClient = anApiClient();
    PatrimoineApi api = new PatrimoineApi(apiClient);

    var data = List.of(
            patrimoine_ilo_updated(),
            patrimoine_to_create()
    );

    var actual = api.crupdatePatrimoines(new GetPatrimoines200Response().data(data));

    System.out.println(actual.getData());

    assertTrue(actual.getData().containsAll(data));
  }
}
