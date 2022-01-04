package com.ftninformatika.jwd.modul3.bioskop.support;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Adresa;
import com.ftninformatika.jwd.modul3.bioskop.service.AdresaService;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.AdresaDTO;

@Component
public class AdresaDtoToAdresa implements Converter<AdresaDTO, Adresa> {

    @Autowired
    private AdresaService adresaService;

    @Override
    public Adresa convert(AdresaDTO adresaDto) {
        Long id = adresaDto.getId();
        Adresa adresa = id == null ? new Adresa() : adresaService.findOne(id).get();

        if(adresa != null) {
            adresa.setId(id);
            adresa.setBroj(adresaDto.getBroj());
            adresa.setUlica(adresaDto.getUlica());
        }

        return adresa;
    }

}

