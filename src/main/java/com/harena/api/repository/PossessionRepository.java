package com.harena.api.repository;

import com.harena.api.file.ExtendedBucketComponent;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
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
  public Possession getByName(String name) {
    return null;
  }

  public Set<Possession> getByPatrimoineName(String patrimoineName, int limit, int offset) {
    return paginateSet(patrimoineRepository.getByName(patrimoineName).possessions(), limit, offset);
  }
}
