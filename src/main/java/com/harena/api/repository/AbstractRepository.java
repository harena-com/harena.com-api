package com.harena.api.repository;

import com.harena.api.file.ExtendedBucketComponent;
import com.harena.api.model.exception.InternalServerErrorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import school.hei.patrimoine.serialisation.Serialiseur;

public abstract class AbstractRepository<T> {
  protected final ExtendedBucketComponent bucketComponent;
  protected final Serialiseur serialiseur;

  protected AbstractRepository(ExtendedBucketComponent bucketComponent) {
    this.bucketComponent = bucketComponent;
    this.serialiseur = new Serialiseur<T>();
  }

  public abstract List<T> getAllPaginated(int limit, int offset);

  public abstract T getByName(String name);

  protected T createFrom(File file) {
    String tAsString;
    try {
      tAsString = Files.readString(file.toPath());
    } catch (IOException e) {
      throw new InternalServerErrorException(e);
    }
    return (T) serialiseur.deserialise(tAsString);
  }

  protected Set<T> paginateSet(Set<T> set, int limit, int offset) {
    List<T> list = new ArrayList<>(set);
    int fromIndex = offset * limit;
    int toIndex = Math.min(fromIndex + limit, list.size());

    if (fromIndex >= list.size()) {
      return new HashSet<>();
    }

    return new HashSet<>(list.subList(fromIndex, toIndex));
  }
}
