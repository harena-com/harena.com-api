package com.harena.api.repository;

import static java.io.File.*;

import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.model.exception.InternalServerErrorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import school.hei.patrimoine.modele.Patrimoine;

@Repository
@Slf4j
public class PatrimoineRepository extends AbstractRepository<Patrimoine> {
  protected PatrimoineRepository(ExtendedBucketComponent bucketComponent) {
    super(bucketComponent);
  }

  @Override
  public List<Patrimoine> getAllPaginated(int limit, int offset) {
    List<File> patrimoineFiles = bucketComponent.getFilesFromS3(limit, offset);
    return patrimoineFiles.stream().map(this::createFrom).toList();
  }

  @Override
  public Optional<Patrimoine> getByName(String patrimoineName) {
    return bucketComponent.getFileFromS3(patrimoineName).stream().map(this::createFrom).findFirst();
  }

  public List<Patrimoine> saveOrUpdateAll(List<Patrimoine> patrimoines) {
    return patrimoines.stream().map(this::saveOrUpdate).collect(Collectors.toList());
  }

  private Patrimoine saveOrUpdate(Patrimoine patrimoine) {
    String fileName = patrimoine.nom();
    try {
      File file = createTempFile(fileName, "");
      if (bucketComponent.getFileFromS3(fileName).isPresent()) {
        bucketComponent.deleteFile(fileName);
      }
      Files.writeString(file.toPath(), serialiseur.serialise(patrimoine));
      bucketComponent.upload(file, fileName);
    } catch (IOException e) {
      throw new InternalServerErrorException(e);
    }
    return patrimoine;
  }
}
