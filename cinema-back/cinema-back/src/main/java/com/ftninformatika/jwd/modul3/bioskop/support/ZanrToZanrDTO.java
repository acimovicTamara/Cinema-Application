package com.ftninformatika.jwd.modul3.bioskop.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ZanrDTO;

@Component
public class ZanrToZanrDTO implements Converter<Zanr, ZanrDTO> {

	@Override
	public ZanrDTO convert(Zanr zanr) {
		ZanrDTO zanrDTO = new ZanrDTO();
		zanrDTO.setId(zanr.getId());
		zanrDTO.setNaziv(zanr.getNaziv());
		return zanrDTO;
	}

	public List<ZanrDTO> convert(List<Zanr> zanrovi) {
		List<ZanrDTO> zanrDto = new ArrayList<>();

		for (Zanr zanr : zanrovi) {
			zanrDto.add(convert(zanr));
		}

		return zanrDto;
	}
}
