package com.ftninformatika.jwd.modul3.bioskop.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ProjekcijaDTO;

@Component
public class ProjekcijaToProjekcijaDTO implements Converter<Projekcija, ProjekcijaDTO> {

	@Autowired
	private FilmToFilmDTO filmToFilmDto;

	@Override
	public ProjekcijaDTO convert(Projekcija projekcija) {
		ProjekcijaDTO projekcijaDTO = new ProjekcijaDTO();
		projekcijaDTO.setId(projekcija.getId());
		projekcijaDTO.setDatumIVreme(projekcija.getDatumIVreme().toString());
		projekcijaDTO.setFilm(filmToFilmDto.convert(projekcija.getFilm()));
		projekcijaDTO.setSala(projekcija.getSala());
		projekcijaDTO.setTip(projekcija.getTip());
		projekcijaDTO.setCenaKarte(projekcija.getCenaKarte());

		return projekcijaDTO;
	}

	public List<ProjekcijaDTO> convert(List<Projekcija> projekcije) {
		List<ProjekcijaDTO> projekcijeDto = new ArrayList<>();

		for (Projekcija projekcija : projekcije) {
			projekcijeDto.add(convert(projekcija));
		}

		return projekcijeDto;
	}

}
