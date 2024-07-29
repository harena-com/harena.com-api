package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.PatrimoineMapper;
import com.harena.api.endpoint.rest.mapper.PossessionMapper;
import com.harena.api.endpoint.rest.model.GetPatrimoinePossessions200Response;
import com.harena.api.endpoint.rest.model.GetPatrimoines200Response;
import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.endpoint.rest.model.PossessionAvecType;
import com.harena.api.model.exception.NotImplementedException;
import com.harena.api.service.PatrimoineService;
import com.harena.api.service.PossessionService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

@RestController
public class PatrimoineController {
  private final PatrimoineService service;
  private final PossessionService possessionService;
  private final PatrimoineMapper mapper;
  private final PossessionMapper possessionMapper;

  public PatrimoineController(
      PatrimoineService service,
      PossessionService possessionService,
      @Qualifier("RestPatrimoineMapper") PatrimoineMapper mapper,
      @Qualifier("RestPossessionMapper") PossessionMapper possessionMapper) {
    this.service = service;
    this.possessionService = possessionService;
    this.mapper = mapper;
    this.possessionMapper = possessionMapper;
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
    List<PossessionAvecType> data =
        mapPossessions(
            possessionService.getPossessionsByPatrimoineName(page, pageSize, nom_patrimoine));
    return new GetPatrimoinePossessions200Response().data(data);
  }

  private List<PossessionAvecType> mapPossessions(Set<Possession> toMap) {
    return toMap.stream()
        .map(
            possession ->
                switch (possession) {
                  case Argent argent -> possessionMapper.toRest(argent);
                  case Materiel materiel -> possessionMapper.toRest(materiel);
                  case FluxArgent fluxArgent -> possessionMapper.toRest(fluxArgent);
                  default -> throw new NotImplementedException(possession.getClass().getName());
                })
        .toList();
  }
}
