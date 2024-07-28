package com.harena.api.repository;

import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.model.exception.NotFoundException;
import java.io.File;
import java.util.List;
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
  public Patrimoine getByName(String patrimoineName) {
    File patrimoineFile =
        bucketComponent
            .getFileFromS3(patrimoineName)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Patrimoine identified with name " + patrimoineName + " not found"));
    return createFrom(patrimoineFile);
  }

  public List<Patrimoine> saveOrUpdateAll(List<Patrimoine> patrimoines) {
    return patrimoines.stream().map(this::saveOrUpdate).collect(Collectors.toList());
  }

  //TODO: make idempotent, here you just write and upload the file
  //Look for an existing file, if you do not find one create, else update
  private Patrimoine saveOrUpdate(Patrimoine patrimoine) {
    File file = new File(patrimoine.nom());
    try {
      Files.writeString(file.toPath(), serialiseur.serialise(patrimoine));
      bucketComponent.upload(file, patrimoine.nom());
    } catch (IOException e) {
      throw new InternalServerErrorException(e);
    }
    return patrimoine;
  }
}
