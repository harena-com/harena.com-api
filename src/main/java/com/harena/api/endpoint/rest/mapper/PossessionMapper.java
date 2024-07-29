package com.harena.api.endpoint.rest.mapper;

import static com.harena.api.endpoint.rest.model.Argent.TypeEnum.AUTRES;
import static com.harena.api.endpoint.rest.model.PossessionAvecType.TypeEnum.*;

import com.harena.api.endpoint.rest.model.*;
import com.harena.api.model.exception.BadRequestException;
import com.harena.api.model.exception.NotImplementedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("RestPossessionMapper")
@AllArgsConstructor
public class PossessionMapper {
  private final DeviseMapper deviseMapper;

  private PossessionAvecType toRest() {
    return new PossessionAvecType();
  }

  public PossessionAvecType toRest(school.hei.patrimoine.modele.possession.Argent domain) {
    return toRest()
        .type(ARGENT)
        .argent(
            new Argent()
                .nom(domain.getNom())
                .t(domain.getT())
                .valeurComptable(domain.getValeurComptable())
                .type(AUTRES)
                // Todo: get devise code
                .devise(new Devise().nom(domain.getDevise().nom()))
                .dateDOuverture(domain.getDateOuverture()));
  }

  public PossessionAvecType toRest(school.hei.patrimoine.modele.possession.Materiel domain) {
    return toRest()
        .type(MATERIEL)
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
        .type(FLUXARGENT)
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
                        .type(AUTRES)
                        // Todo: get devise code
                        .devise(new Devise().nom(domain.getArgent().getDevise().nom()))
                        .dateDOuverture(domain.getArgent().getDateOuverture()))
                .debut(domain.getDebut())
                .fin(domain.getFin())
                .fluxMensuel(domain.getFluxMensuel())
                .dateDOperation(domain.getDateOperation()));
  }

  public school.hei.patrimoine.modele.possession.Possession toDomain(PossessionAvecType rest) {
    return switch (rest.getType()) {
      case MATERIEL -> new school.hei.patrimoine.modele.possession.Materiel(
          rest.getMateriel().getNom(),
          rest.getMateriel().getT(),
          rest.getMateriel().getValeurComptable(),
          rest.getMateriel().getDateDAcquisition(),
          rest.getMateriel().getTauxDappreciationAnnuel());
      case FLUXARGENT -> new school.hei.patrimoine.modele.possession.FluxArgent(
          rest.getFluxArgent().getNom(),
          new school.hei.patrimoine.modele.possession.Argent(
              rest.getArgent().getNom(),
              rest.getArgent().getT(),
              rest.getArgent().getT(),
              rest.getArgent().getValeurComptable(),
              deviseMapper.toDomain(rest.getArgent().getDevise())),
          rest.getFluxArgent().getDebut(),
          rest.getFluxArgent().getFin(),
          rest.getFluxArgent().getFluxMensuel(),
          rest.getFluxArgent().getDateDOperation());
      case ARGENT -> new school.hei.patrimoine.modele.possession.Argent(
          rest.getArgent().getNom(),
          rest.getArgent().getT(),
          rest.getArgent().getT(),
          rest.getArgent().getValeurComptable(),
          deviseMapper.toDomain(rest.getArgent().getDevise()));
      case null -> throw new BadRequestException("The possession is null");
      default -> throw new NotImplementedException("Not implemented " + rest.getType());
    };
  }
}
