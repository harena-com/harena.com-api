package com.harena.api.service;

import com.harena.api.endpoint.rest.mapper.PossessionMapper;
import com.harena.api.repository.PossessionRepository;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.possession.Possession;

@Service
@AllArgsConstructor
public class PossessionService {
  private final PossessionRepository repository;
  private PossessionMapper mapper;

  public Set<Possession> getPossessionsByPatrimoineName(
      int page, int pageSize, String nomPatrimoine) {
    return repository.getByPatrimoineName(nomPatrimoine, pageSize, page);
  }
}
