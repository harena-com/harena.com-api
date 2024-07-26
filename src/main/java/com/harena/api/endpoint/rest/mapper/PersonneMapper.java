package com.harena.api.endpoint.rest.mapper;

import com.harena.api.endpoint.rest.model.Personne;
import org.springframework.stereotype.Component;

@Component
public class PersonneMapper {

  public Personne toRest(school.hei.patrimoine.modele.Personne domain) {
    return new Personne().nom(domain.nom());
  }
}
