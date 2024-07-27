package com.harena.api.endpoint.rest.mapper;

import com.harena.api.endpoint.rest.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PossessionMapper {
  private PossessionAvecType toRest() {
    return new PossessionAvecType();
  }

  public PossessionAvecType toRest(school.hei.patrimoine.modele.possession.Argent domain) {
    return toRest()
        .type(PossessionAvecType.TypeEnum.ARGENT)
        .argent(
            new Argent()
                .nom(domain.getNom())
                .t(domain.getT())
                .valeurComptable(domain.getValeurComptable())
                .type(Argent.TypeEnum.AUTRES)
                // Todo: get devise code
                .devise(new Devise().nom(domain.getDevise().nom()))
                .dateDOuverture(domain.getDateOuverture()));
  }

  public PossessionAvecType toRest(school.hei.patrimoine.modele.possession.Materiel domain) {
    return toRest()
        .type(PossessionAvecType.TypeEnum.MATERIEL)
        .materiel(
            new Materiel()
                .nom(domain.getNom())
                .t(domain.getT())
                .valeurComptable(domain.getValeurComptable())
                // Todo: get devise code
                .devise(new Devise().nom(domain.getDevise().nom()))
            // Todo: get dateDAcquisition and tauxDappreciationAnnuel
            );
  }

  public PossessionAvecType toRest(school.hei.patrimoine.modele.possession.FluxArgent domain) {
    return toRest()
        .type(PossessionAvecType.TypeEnum.FLUXARGENT)
        .fluxArgent(
            new FluxArgent()
                .t(domain.getT())
                .nom(domain.getNom())
                .valeurComptable(domain.getValeurComptable())
                // Todo: get devise code
                .devise(new Devise().nom(domain.getDevise().nom()))
                .argent(
                    new Argent()
                        .nom(domain.getArgent().getNom())
                        .t(domain.getArgent().getT())
                        .valeurComptable(domain.getArgent().getValeurComptable())
                        .type(Argent.TypeEnum.AUTRES)
                        // Todo: get devise code
                        .devise(new Devise().nom(domain.getArgent().getDevise().nom()))
                        .dateDOuverture(domain.getArgent().getDateOuverture()))
                .debut(domain.getDebut())
                .fin(domain.getFin())
                .fluxMensuel(domain.getFluxMensuel())
                .dateDOperation(domain.getDateOperation()));
  }
}