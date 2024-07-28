package com.harena.api.service;

import com.harena.api.repository.PatrimoineRepository;
import com.harena.api.repository.mapper.PatrimoineMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;

@Service
public class PatrimoineService {
  private final PatrimoineRepository repository;
  private final PatrimoineMapper mapper;

  public PatrimoineService(
      PatrimoineRepository repository,
      @Qualifier("DomainPatrimoineMapper") PatrimoineMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<Patrimoine> getPaginatedPatrimoines(int page, int pageSize) {
    return repository.getAllPaginated(pageSize, page);
  }

  public Patrimoine getPatrimoineByName(String patrimoineName) {
    return repository.getByName(patrimoineName);
  }

  public List<Patrimoine> crupdatePatrimoines(
      List<com.harena.api.endpoint.rest.model.Patrimoine> patrimoines) {
    List<school.hei.patrimoine.modele.Patrimoine> domainPatrimoines =
        patrimoines.stream().map(mapper::toDomain).collect(Collectors.toList());
    return repository.saveOrUpdateAll(domainPatrimoines);
  }
}
