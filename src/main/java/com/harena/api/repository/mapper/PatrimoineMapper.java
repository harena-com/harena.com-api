package com.harena.api.repository.mapper;

import com.harena.api.service.PossessionService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Possession;

@Component("DomainPatrimoineMapper")
public class PatrimoineMapper {
  private final PersonneMapper personneMapper;
  private final PossessionService possessionService;

  public PatrimoineMapper(
      @Qualifier("DomainPersonneMapper") PersonneMapper personneMapper,
      PossessionService possessionService) {
    this.personneMapper = personneMapper;
    this.possessionService = possessionService;
  }

  public Patrimoine toDomain(com.harena.api.endpoint.rest.model.Patrimoine rest) {
    return new Patrimoine(
        rest.getNom(),
        personneMapper.toDomain(rest.getPossesseur()),
        rest.getT(),
        getPatrimoinePossessions(rest.getNom()));
  }

  private Set<Possession> getPatrimoinePossessions(String patrimoineName) {
    return possessionService.getPossessionsByPatrimoineName(0, Integer.MAX_VALUE, patrimoineName);
  }
}
