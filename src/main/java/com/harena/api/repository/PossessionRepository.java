package com.harena.api.repository;

import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.model.exception.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Possession;

@Repository
public class PossessionRepository extends AbstractRepository<Possession> {
  private final PatrimoineRepository patrimoineRepository;

  protected PossessionRepository(
      ExtendedBucketComponent bucketComponent, PatrimoineRepository patrimoineRepository) {
    super(bucketComponent);
    this.patrimoineRepository = patrimoineRepository;
  }

  @Override
  public List<Possession> getAllPaginated(int limit, int offset) {
    return List.of();
  }

  @Override
  public Optional<Possession> getByName(String name) {
    return null;
  }

  public Set<Possession> getByPatrimoineName(String patrimoineName, int limit, int offset) {
    Optional<Patrimoine> foundPatrimoine = patrimoineRepository.getByName(patrimoineName);
    if (foundPatrimoine.isPresent()) {
      return foundPatrimoine.get().possessions();
    } else {
      return Set.of();
    }
  }

  public List<Possession> saveOrUpdate(String patrimoine_nom, Set<Possession> possessions) {
    var patrimoineOptional = patrimoineRepository.getByName(patrimoine_nom);

    if (patrimoineOptional.isEmpty()) {
      throw new NotFoundException("Not found patrimoine: " + patrimoine_nom);
    }
    var patrimoine = patrimoineOptional.get();
    var result =
        patrimoineRepository.saveOrUpdateAll(
            Collections.singletonList(
                new Patrimoine(
                    patrimoine.nom(), patrimoine.possesseur(), patrimoine.t(), possessions)));
    return result.getFirst().possessions().stream().toList();
  }
}
