package com.ftninformatika.jwd.modul3.bioskop.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul3.bioskop.service.ZanrService;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ZanrDTO;

@Component
public class ZanrDtoToZanr implements Converter<ZanrDTO, Zanr> {

	@Autowired
	private ZanrService zanrService;

	@Override
	public Zanr convert(ZanrDTO zanrDto) {
		Zanr zanr;

		if (zanrDto.getId() == null) {
			zanr = new Zanr();
		} else {
			zanr = zanrService.findOne(zanrDto.getId());
		}

		if (zanr != null) {
			zanr.setNaziv(zanrDto.getNaziv());
		}
		return zanr;
	}

}
