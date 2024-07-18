package com.harena.api.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import com.harena.api.PojaGenerated;

@PojaGenerated
public class EmailConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.ses.source", () -> "dummy-ses-source");
  }
}
