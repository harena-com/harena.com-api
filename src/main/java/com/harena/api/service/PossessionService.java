package com.harena.api.service;

import com.harena.api.endpoint.rest.mapper.PossessionMapper;
import com.harena.api.endpoint.rest.model.PossessionAvecType;
import com.harena.api.model.exception.NotImplementedException;
import com.harena.api.repository.PossessionRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;

@Service
@AllArgsConstructor
public class PossessionService {
  private final PossessionRepository repository;
  private PossessionMapper mapper;

  public List<PossessionAvecType> getPossessionsByPatrimoineName(
      int page, int pageSize, String nomPatrimoine) {
    return repository.getByPatrimoineName(nomPatrimoine, pageSize, page).stream()
        .map(
            possession ->
                switch (possession) {
                  case Argent argent -> mapper.toRest(argent);
                  case Materiel materiel -> mapper.toRest(materiel);
                  case FluxArgent fluxArgent -> mapper.toRest(fluxArgent);
                  default -> throw new NotImplementedException(possession.getClass().getName());
                })
        .toList();
  }
}
