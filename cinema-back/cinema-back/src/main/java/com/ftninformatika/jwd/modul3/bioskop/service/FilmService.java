package com.ftninformatika.jwd.modul3.bioskop.service;

import java.util.List;

import com.ftninformatika.jwd.modul3.bioskop.model.Film;

public interface FilmService {

	Film findOne(Long id);

	List<Film> findAll();

	Film save(Film film);

	Film update(Film film);

	Film delete(Long id);

	List<Film> find(String naziv, Long zanrId, Integer trajanjeOd, Integer trajanjeDo);

	List<Film> findByZanrId(Long zanrId);
}
