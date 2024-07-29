package com.harena.api.service;

import com.harena.api.model.exception.NotFoundException;
import com.harena.api.repository.PossessionRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.possession.Possession;

@Service
@AllArgsConstructor
public class PossessionService {
  private final PossessionRepository repository;

  public Set<Possession> getPossessionsByPatrimoineName(
      int page, int pageSize, String nomPatrimoine) {
    return repository.getByPatrimoineName(nomPatrimoine, pageSize, page);
  }

  public Possession getPatrimoinePossessionByNom(String patrimoineName, String name) {
    return repository
        .getPatrimoinePossessionByName(patrimoineName, name)
        .orElseThrow(
            () -> new NotFoundException("Possession identified with name " + name + " not found"));
  }

  public List<Possession> crupdatePossessionByPatrimoinesName(
          String patrimoine_nom, List<Possession> possessions) {
    return repository.saveOrUpdate(patrimoine_nom, new HashSet<>(possessions));
  }
}
