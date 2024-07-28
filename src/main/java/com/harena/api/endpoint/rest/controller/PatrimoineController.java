package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.PatrimoineMapper;
import com.harena.api.endpoint.rest.model.GetPatrimoinePossessions200Response;
import com.harena.api.endpoint.rest.model.GetPatrimoines200Response;
import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.service.PatrimoineService;
import com.harena.api.service.PossessionService;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatrimoineController {
  private final PatrimoineService service;
  private final PossessionService possessionService;
  private final PatrimoineMapper mapper;

    public PatrimoineController(PatrimoineService service, @Qualifier("RestPatrimoineMapper") PatrimoineMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/patrimoines")
  public GetPatrimoines200Response getPatrimoines(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
    List<Patrimoine> data =
        service.getPaginatedPatrimoines(page, pageSize).stream().map(mapper::toRest).toList();
    return new GetPatrimoines200Response().data(data);
  }

  @GetMapping("/patrimoines/{nom_patrimoine}")
  public Patrimoine getPatrimoineByNom(@PathVariable("nom_patrimoine") String patrimoineName) {
    return mapper.toRest(service.getPatrimoineByName(patrimoineName));
  }

  @PutMapping("/patrimoines")
  public GetPatrimoines200Response crupdatePatrimoines(
      @RequestBody GetPatrimoines200Response patrimoines) {
    List<Patrimoine> updatedPatrimoines =
        service.crupdatePatrimoines(patrimoines.getData()).stream().map(mapper::toRest).toList();
    return new GetPatrimoines200Response().data(updatedPatrimoines);
  }

  @GetMapping("/patrimoines/{nom_patrimoine}/possessions")
  public GetPatrimoinePossessions200Response getPatrimoines(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
      @PathVariable String nom_patrimoine) {
    return new GetPatrimoinePossessions200Response()
        .data(possessionService.getPossessionsByPatrimoineName(page, pageSize, nom_patrimoine));
  }
}
