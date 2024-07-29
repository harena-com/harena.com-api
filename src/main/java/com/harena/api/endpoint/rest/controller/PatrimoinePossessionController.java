package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.PossessionMapper;
import com.harena.api.endpoint.rest.model.PossessionAvecType;
import com.harena.api.service.PossessionService;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PatrimoinePossessionController {
  private final PossessionService service;
  private final PossessionMapper mapper;

  @GetMapping("/patrimoines/{nom_patrimoine}/possessions/{nom_possession}")
  public PossessionAvecType getPatrimoinePossessionByNom(
      @PathVariable(name = "nom_patrimoine") String nomPatrimoine,
      @PathVariable(name = "nom_possession") String nomPossession) {
    return mapper
        .mapPossessions(Set.of(service.getPatrimoinePossessionByNom(nomPatrimoine, nomPossession)))
        .getFirst();
  }
}
