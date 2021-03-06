package com.ftninformatika.jwd.modul3.bioskop.support;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul3.bioskop.service.FilmService;
import com.ftninformatika.jwd.modul3.bioskop.service.ProjekcijaService;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ProjekcijaDTO;

@Component
public class ProjekcijaDtoToProjekcija implements Converter<ProjekcijaDTO, Projekcija> {

	@Autowired
	private ProjekcijaService projekcijaService;

	@Autowired
	private FilmService filmService;

	@Override
	public Projekcija convert(ProjekcijaDTO dto) {
		Projekcija projekcija;

		if (dto.getId() == null) {
			projekcija = new Projekcija();
		} else {
			projekcija = projekcijaService.findOne(dto.getId());
		}

		if (projekcija != null) {
			projekcija.setDatumIVreme(getLocalDateTime(dto.getDatumIVreme()));
			projekcija.setFilm(filmService.findOne(dto.getFilm().getId()));
			projekcija.setSala(dto.getSala());
			projekcija.setTip(dto.getTip());
			projekcija.setCenaKarte(dto.getCenaKarte());
		}
		return projekcija;
	}

	private LocalDateTime getLocalDateTime(String datumIVreme) throws DateTimeParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate datum = LocalDate.parse(datumIVreme.substring(0, 10), formatter);
		LocalTime vreme = LocalTime.parse(datumIVreme.substring(11), DateTimeFormatter.ofPattern("HH:mm"));
		return LocalDateTime.of(datum, vreme);
	}

}
