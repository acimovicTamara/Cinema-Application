package com.ftninformatika.jwd.modul3.bioskop.service;

import java.util.List;

import com.ftninformatika.jwd.modul3.bioskop.model.Zanr;


public interface ZanrService {

	Zanr findOne(Long id);

    List<Zanr> findAll();

    List<Zanr> find(List<Long> ids);

    Zanr save(Zanr zanr);

    Zanr update(Zanr zanr);

    Zanr delete(Long id);

    List<Zanr> find(String naziv);
}
