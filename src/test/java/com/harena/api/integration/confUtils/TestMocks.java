package com.harena.api.integration.confUtils;

import static com.harena.api.endpoint.rest.model.Argent.TypeEnum.AUTRES;
import static com.harena.api.endpoint.rest.model.PossessionAvecType.TypeEnum.ARGENT;
import static com.harena.api.endpoint.rest.model.PossessionAvecType.TypeEnum.FLUXARGENT;
import static java.time.Month.AUGUST;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JUNE;
import static java.time.Month.MAY;

import com.harena.api.endpoint.rest.model.*;
import java.time.LocalDate;

public class TestMocks {
  static final LocalDate au13mai24 = LocalDate.of(2024, MAY, 13);

  public static Personne ilo() {
    return new Personne().nom("Ilo");
  }

  public static Patrimoine patrimoine_ilo() {
    return new Patrimoine()
        .nom("patrimoineIloAu13mai24")
        .possesseur(ilo())
        .t(au13mai24)
        .valeurComptable(600_000);
  }

  public static Patrimoine patrimoine_ilo_updated() {
    return patrimoine_ilo().t(LocalDate.of(2024, JUNE, 26));
  }

  public static Patrimoine patrimoine_to_create() {
    return new Patrimoine()
        .nom("patrimoineToCreate")
        .possesseur(ilo())
        .t(LocalDate.of(2024, JUNE, 26))
        .valeurComptable(0);
  }

  public static PossessionAvecType argent_espece_de_ilo() {
    return new PossessionAvecType()
        .type(ARGENT)
        .argent(
            new Argent()
                .t(au13mai24)
                .nom("Espèces")
                .valeurComptable(600_000)
                .devise(new Devise().nom("non-nommee"))
                .dateDOuverture(au13mai24)
                .type(AUTRES));
  }

  public static PossessionAvecType vie_courant_de_ilo() {
    return new PossessionAvecType()
        .type(ARGENT)
        .type(FLUXARGENT)
        .fluxArgent(
            new FluxArgent()
                .nom("Vie courante")
                .valeurComptable(0)
                .devise(new Devise().nom("non-nommee"))
                .argent(argent_espece_de_ilo().getArgent())
                .debut(LocalDate.of(2024, FEBRUARY, 3))
                .fin(LocalDate.of(2024, AUGUST, 21))
                .fluxMensuel(-100_000)
                .dateDOperation(15));
  }
}
