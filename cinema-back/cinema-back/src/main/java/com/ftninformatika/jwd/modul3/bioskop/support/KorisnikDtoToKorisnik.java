package com.ftninformatika.jwd.modul3.bioskop.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.bioskop.model.Korisnik;
import com.ftninformatika.jwd.modul3.bioskop.service.AdresaService;
import com.ftninformatika.jwd.modul3.bioskop.service.KorisnikService;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.KorisnikDTO;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class KorisnikDtoToKorisnik implements Converter<KorisnikDTO, Korisnik> {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private AdresaService adresaService;

    @Override
    public Korisnik convert(KorisnikDTO korisnikDTO) {
        Korisnik korisnik = null;
        if(korisnikDTO.getId() != null) {
            korisnik = korisnikService.findOne(korisnikDTO.getId()).get();
        }

        if(korisnik == null) {
            korisnik = new Korisnik();
        }

        korisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
        korisnik.setAdresa(adresaService.findOne(korisnikDTO.getAdresaDTO().getId()).get());
        korisnik.seteMail(korisnikDTO.geteMail());
        korisnik.setIme(korisnikDTO.getIme());
        korisnik.setPrezime(korisnikDTO.getPrezime());

        return korisnik;
    }

}
