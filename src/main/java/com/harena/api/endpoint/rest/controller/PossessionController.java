package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.model.GetPatrimoinePossessions200Response;
import com.harena.api.service.PossessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/patrimoines/{nom_patrimoine}/possessions")
public class PossessionController {
  private final PossessionService service;

  @GetMapping
  public GetPatrimoinePossessions200Response getPatrimoines(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
      @PathVariable String nom_patrimoine) {
    return new GetPatrimoinePossessions200Response()
        .data(service.getPossessionsByPatrimoineName(page, pageSize, nom_patrimoine));
  }
}
