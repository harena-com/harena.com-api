package com.harena.api.repository.mapper;

import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.Personne;

@Component("DomainPersonneMapper")
public class PersonneMapper {

  public Personne toDomain(com.harena.api.endpoint.rest.model.Personne rest) {
    return new Personne(rest.getNom());
  }
}
