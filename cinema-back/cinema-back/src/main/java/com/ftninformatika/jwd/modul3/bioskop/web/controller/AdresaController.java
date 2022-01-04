package com.ftninformatika.jwd.modul3.bioskop.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ftninformatika.jwd.modul3.bioskop.model.Adresa;
import com.ftninformatika.jwd.modul3.bioskop.service.AdresaService;
import com.ftninformatika.jwd.modul3.bioskop.support.AdresaDtoToAdresa;
import com.ftninformatika.jwd.modul3.bioskop.support.AdresaToAdresaDto;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.AdresaDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/adrese", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdresaController {

    @Autowired
    private AdresaService adresaService;

    @Autowired
    private AdresaDtoToAdresa toAdresa;

    @Autowired
    private AdresaToAdresaDto toAdresaDto;

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdresaDTO> create(@RequestBody AdresaDTO adresaDTO){

        Adresa adresa = toAdresa.convert(adresaDTO);
        Adresa sacuvanaAdresa = adresaService.save(adresa);

        return new ResponseEntity<>(toAdresaDto.convert(sacuvanaAdresa), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
    @PutMapping(value= "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdresaDTO> edit( @PathVariable Long id, @RequestBody AdresaDTO adresaDTO){

        if(!id.equals(adresaDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Adresa adresa = toAdresa.convert(adresaDTO);
        Adresa sacuvanaAdresa = adresaService.save(adresa);

        return new ResponseEntity<>(toAdresaDto.convert(sacuvanaAdresa), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        Optional<Adresa> adresa = adresaService.findOne(id);
        if(!adresa.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!adresa.get().getKorisnici().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        adresaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AdresaDTO> getOne(@PathVariable Long id){
        Adresa adresa = adresaService.findOne(id).get();

        if(adresa != null) {
            return new ResponseEntity<>(toAdresaDto.convert(adresa), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AdresaDTO>> getAll(){

        List<Adresa> adrese = adresaService.findAll();

        return new ResponseEntity<>(toAdresaDto.convert(adrese), HttpStatus.OK);
    }
}
