package com.harena.api.repository.mapper;

import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.Patrimoine;

@Component("DomainPatrimoineMapper")
public class PatrimoineMapper {
  private final PersonneMapper personneMapper;

  public PatrimoineMapper(@Qualifier("DomainPersonneMapper") PersonneMapper personneMapper) {
    this.personneMapper = personneMapper;
  }

  //Note: Leave Patrimoine.possessions() to an empty Set in the domain Mapper because it is not provided
  // by the rest model
  // Use possession service to get a patrimoine's possession if needed
  public Patrimoine toDomain(com.harena.api.endpoint.rest.model.Patrimoine rest) {
    return new Patrimoine(
        rest.getNom(), personneMapper.toDomain(rest.getPossesseur()), rest.getT(), Set.of());
  }
}
