package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.PatrimoineMapper;
import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.service.PatrimoineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PatrimoineController {
    private final PatrimoineService service;
    private final PatrimoineMapper mapper;

    @GetMapping("/patrimoines")
    public List<Patrimoine> getPatrimoines(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "page_size", defaultValue = "10") int pageSize){
        return service.getPaginatedPatrimoines(page, pageSize).stream().map(mapper::toRest).toList();
    }
}
