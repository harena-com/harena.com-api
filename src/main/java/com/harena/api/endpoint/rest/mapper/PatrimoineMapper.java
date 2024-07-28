package com.harena.api.endpoint.rest.mapper;

import com.harena.api.endpoint.rest.model.Patrimoine;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.Possession;
import school.hei.patrimoine.modele.possession.Possession;

@Component
@AllArgsConstructor
public class PatrimoineMapper {
  private final PersonneMapper personneMapper;
  private final PossessionMapper possessionMapper;

  public Patrimoine toRest(school.hei.patrimoine.modele.Patrimoine domain) {
    return new Patrimoine()
        .nom(domain.nom())
        .t(domain.t())
        .valeurComptable(domain.getValeurComptable())
        .possesseur(personneMapper.toRest(domain.possesseur()));
  }

  public school.hei.patrimoine.modele.Patrimoine toDomain(Patrimoine rest) {
    Personne possesseur = personneMapper.toDomain(rest.getPossesseur());
    Set<Possession> possessions =
        rest.getPossessions().stream().map(possessionMapper::toDomain).collect(Collectors.toSet());
    return new school.hei.patrimoine.modele.Patrimoine(
        rest.getNom(), possesseur, rest.getT(), possessions);
  }
}
