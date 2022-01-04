package com.ftninformatika.jwd.modul3.bioskop.service;


import java.util.List;
import java.util.Optional;

import com.ftninformatika.jwd.modul3.bioskop.model.Adresa;

public interface AdresaService {

    Optional<Adresa> findOne(Long id);

    List<Adresa> findAll();

    Adresa save(Adresa adresa);

    void delete(Long id);

}
