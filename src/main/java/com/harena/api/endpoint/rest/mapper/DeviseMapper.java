package com.harena.api.endpoint.rest.mapper;

import com.harena.api.endpoint.rest.model.Devise;
import org.springframework.stereotype.Component;;import java.time.LocalDate;

@Component
public class DeviseMapper {

    public Devise toRest(school.hei.patrimoine.modele.Devise domain) {
        return new Devise()
                .nom(domain.nom())
                .code(domain.nom());
    }

    public school.hei.patrimoine.modele.Devise toDomain(Devise rest) {
        return new school.hei.patrimoine.modele.Devise(
                rest.getNom(),
                0,
                LocalDate.now(),
                0.0
        );
    }
}
