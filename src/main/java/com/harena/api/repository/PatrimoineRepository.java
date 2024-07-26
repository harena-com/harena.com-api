package com.harena.api.repository;

import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.model.exception.InternalServerErrorException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.*;

@Repository
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
    try {
      String patrimoineAsString = FileUtils.readFileToString(patrimoineFile, UTF_8);
      return (Patrimoine) serialiseur.deserialise(patrimoineAsString);
    } catch (IOException e) {
      throw new InternalServerErrorException(e);
    }
  }
}
