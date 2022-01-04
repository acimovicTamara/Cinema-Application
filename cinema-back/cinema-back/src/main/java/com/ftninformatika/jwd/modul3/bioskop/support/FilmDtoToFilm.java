package com.ftninformatika.jwd.modul3.bioskop.support;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Film;
import com.ftninformatika.jwd.modul3.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul3.bioskop.service.FilmService;
import com.ftninformatika.jwd.modul3.bioskop.service.ZanrService;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.FilmDTO;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ZanrDTO;

@Component
public class FilmDtoToFilm implements Converter<FilmDTO, Film> {

	@Autowired
	private FilmService filmService;

	@Autowired
	private ZanrService zanrService;

	@Override
	public Film convert(FilmDTO dto) {
		Film entity;

		if (dto.getId() == null) {
			entity = new Film();
		} else {
			entity = filmService.findOne(dto.getId());
		}

		if (entity != null) {
			entity.setNaziv(dto.getNaziv());
			entity.setTrajanje(dto.getTrajanje());

			if (dto.getZanrovi() != null) {
				List<Long> idZanrova = dto.getZanrovi().stream().map(ZanrDTO::getId).collect(Collectors.toList());
				List<Zanr> zanrovi = zanrService.find(idZanrova);
				entity.setZanrovi(new HashSet<>(zanrovi));
			}
		}

		return entity;
	}

}
