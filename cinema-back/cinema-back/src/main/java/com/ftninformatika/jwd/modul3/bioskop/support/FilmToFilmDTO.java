package com.ftninformatika.jwd.modul3.bioskop.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Film;
import com.ftninformatika.jwd.modul3.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.FilmDTO;

@Component
public class FilmToFilmDTO implements Converter<Film, FilmDTO> {

	@Autowired
	private ZanrToZanrDTO zanrToZanrDto;

	@Override
	public FilmDTO convert(Film film) {
		FilmDTO dto = new FilmDTO();
		dto.setId(film.getId());
		dto.setNaziv(film.getNaziv());
		dto.setTrajanje(film.getTrajanje());
		List<Zanr> zanrovi = new ArrayList<>(film.getZanrovi());
		dto.setZanrovi(new HashSet<>(zanrToZanrDto.convert(zanrovi)));
		return dto;
	}

	public List<FilmDTO> convert(List<Film> filmovi) {
		List<FilmDTO> filmoviDto = new ArrayList<>();

		for (Film film : filmovi) {
			filmoviDto.add(convert(film));
		}

		return filmoviDto;
	}

}
