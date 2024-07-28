package com.harena.api.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.harena.api.conf.FacadeIT;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@AutoConfigureMockMvc
public class CorsIT extends FacadeIT {
  @LocalServerPort private int port;

  @Test
  void ping_with_cors() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + port;

    HttpResponse<String> response =
        client.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/ping"))
                // cors
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://localhost:3000")
                .build(),
            HttpResponse.BodyHandlers.ofString());
    assertEquals(HttpStatus.OK.value(), response.statusCode());
    assertEquals("pong", response.body());
    // cors
    var headers = response.headers();
    var origins = headers.allValues("Access-Control-Allow-Origin");
    assertEquals(1, origins.size());
    assertEquals("*", origins.get(0));
  }
}
