package com.harena.api.repository;

import static java.nio.charset.StandardCharsets.*;

import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.model.exception.InternalServerErrorException;
import com.harena.api.model.exception.NotImplementedException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Possession;
import school.hei.patrimoine.serialisation.Serialiseur;

@Repository
@Slf4j
public class PatrimoineRepository {
  private final ExtendedBucketComponent bucketComponent;
  private final Serialiseur serialiseur;

  public PatrimoineRepository(ExtendedBucketComponent bucketComponent) {
    this.bucketComponent = bucketComponent;
    this.serialiseur = new Serialiseur<Patrimoine>();
  }

  public List<Patrimoine> getAllPaginatedPatrimoines(int limit, int offset) {
    List<File> patrimoineFiles = bucketComponent.getFilesFromS3(limit, offset);
    return patrimoineFiles.stream().map(this::createPatrimoineFrom).toList();
  }

  private Patrimoine createPatrimoineFrom(File patrimoineFile) {
    String patrimoineAsString;
    try {
      patrimoineAsString = Files.readString(patrimoineFile.toPath());
    } catch (IOException e) {
      throw new InternalServerErrorException(e);
    }
    return (Patrimoine) serialiseur.deserialise(patrimoineAsString);
  }

  public List<Possession> getPossessionsByPatrimoineName(String nom) {
    // Todo: implement patrimoine get by nom
    throw new NotImplementedException("No functionality to find by nom");
  }
}
