package com.harena.api.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.patrimoine.modele.Patrimoine;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class PatrimoineRepository {
    private Set<Patrimoine> patrimoines;

    private Set<Patrimoine> getPatrimoines() {
        return patrimoines;
    }

    private void setPatrimoines(Set<Patrimoine> patrimoines) {
        this.patrimoines = patrimoines;
    }

    public List<Patrimoine> getAll() {
        return getPatrimoines().stream().toList();
    }

    public Patrimoine add(Patrimoine patrimoine) {
        var r = getPatrimoines();
        r.add(patrimoine);
        setPatrimoines(r);
        return patrimoine;
    }

    public List<Patrimoine> add(List<Patrimoine> patrimoine) {
        for (Patrimoine p : patrimoine) {
            add(p);
        }
        return patrimoine;
    }

    public Optional<Patrimoine> getByName(String name) {
        return getPatrimoines().stream().filter(p -> p.nom().equals(name)).findFirst();
    }

    public Optional<Patrimoine> delete(String name) {
        var result = getByName(name);
        setPatrimoines(getPatrimoines().stream().filter(p -> result.orElse(null) != p).collect(Collectors.toSet()));
        return result;
    }
}
