package com.harena.api.service;

import com.harena.api.endpoint.rest.mapper.PatrimoineMapper;
import com.harena.api.repository.PatrimoineRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;

@Service
@AllArgsConstructor
public class PatrimoineService {
  private final PatrimoineRepository repository;
  private final PatrimoineMapper mapper;

  public List<Patrimoine> getPaginatedPatrimoines(int page, int pageSize) {
    return repository.getAllPaginated(pageSize, page);
  }

  public Patrimoine getPatrimoineByName(String patrimoineName) {
    return repository.getByName(patrimoineName);
  }

  public List<Patrimoine> crupdatePatrimoines(List<Patrimoine> patrimoines) {
    List<school.hei.patrimoine.modele.Patrimoine> domainPatrimoines =
        patrimoines.stream().map(mapper::toDomain).collect(Collectors.toList());
    return repository.saveOrUpdateAll(domainPatrimoines);
  }
}
