package com.harena.api.integration.confUtils;

import static java.time.Month.MAY;

import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.endpoint.rest.model.Personne;
import java.time.LocalDate;

public class TestMocks {
  public static Personne ilo() {
    return new Personne().nom("Ilo");
  }

  public static Patrimoine patrimoine_ilo() {
    return new Patrimoine()
        .nom("patrimoineIloAu13mai24")
        .possesseur(ilo())
        .t(LocalDate.of(2024, MAY, 13))
        .valeurComptable(600_000);
  }
}
