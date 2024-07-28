package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.PatrimoineMapper;
import com.harena.api.endpoint.rest.model.GetPatrimoines200Response;
import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.service.PatrimoineService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PatrimoineController {
  private final PatrimoineService service;
  private final PatrimoineMapper mapper;

  @GetMapping("/patrimoines")
  public GetPatrimoines200Response getPatrimoines(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
    List<Patrimoine> data =
        service.getPaginatedPatrimoines(page, pageSize).stream().map(mapper::toRest).toList();
    return new GetPatrimoines200Response().data(data);
  }

  @GetMapping("/patrimoines/{nom_patrimoine}")
  public Patrimoine getPatrimoineByNom(
      @PathVariable("nom_patrimoine") String patrimoineName) {
      return mapper.toRest(service.getPatrimoineByName(patrimoineName));
  }
}
