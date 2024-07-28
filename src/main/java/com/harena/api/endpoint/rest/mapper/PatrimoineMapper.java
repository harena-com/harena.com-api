package com.harena.api.endpoint.rest.mapper;

import com.harena.api.endpoint.rest.model.Patrimoine;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("RestPatrimoineMapper")
public class PatrimoineMapper {
  private final PersonneMapper personneMapper;

  public PatrimoineMapper(@Qualifier("RestPersonneMapper") PersonneMapper personneMapper) {
    this.personneMapper = personneMapper;
  }

  public Patrimoine toRest(school.hei.patrimoine.modele.Patrimoine domain) {
    return new Patrimoine()
        .nom(domain.nom())
        .t(domain.t())
        .valeurComptable(domain.getValeurComptable())
        .possesseur(personneMapper.toRest(domain.possesseur()));
  }
}
