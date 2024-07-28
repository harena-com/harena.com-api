package com.harena.api.service;

import com.harena.api.repository.PatrimoineRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;

@Service
@AllArgsConstructor
public class PatrimoineService {
  private final PatrimoineRepository repository;

  public List<Patrimoine> getPaginatedPatrimoines(int page, int pageSize) {
    return repository.getAllPaginatedPatrimoines(pageSize, page);
  }

  public Patrimoine getPatrimoineByName(String patrimoineName) {
    return repository.getPatrimoineByName(patrimoineName);
  }
}
