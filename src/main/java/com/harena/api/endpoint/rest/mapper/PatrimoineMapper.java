package com.harena.api.endpoint.rest.mapper;

import com.harena.api.endpoint.rest.model.Patrimoine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatrimoineMapper {
    private final PersonneMapper personneMapper;

    public Patrimoine toRest(school.hei.patrimoine.modele.Patrimoine domain){
        return new Patrimoine()
                .nom(domain.nom())
                .t(domain.t())
                .valeurComptable(domain.getValeurComptable())
                .possesseur(personneMapper.toRest(domain.possesseur()));
    }
}
