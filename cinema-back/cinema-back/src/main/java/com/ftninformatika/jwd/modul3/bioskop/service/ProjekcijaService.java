package com.ftninformatika.jwd.modul3.bioskop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ftninformatika.jwd.modul3.bioskop.model.Projekcija;


public interface ProjekcijaService {

	Projekcija findOne(Long id);

	Page<Projekcija> findAll(Integer pageNo);

	Projekcija save(Projekcija projekcija);

	Projekcija update(Projekcija projekcija);

	Projekcija delete(Long id);

	Page<Projekcija> find(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long filmId, String tip,
			Integer sala, Double cenaKarteOd, Double cenaKarteDo, Integer pageNo);

	List<Projekcija> findByFilmId(Long filmId);
}
