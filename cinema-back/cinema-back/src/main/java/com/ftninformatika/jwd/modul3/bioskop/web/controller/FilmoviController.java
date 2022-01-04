package com.ftninformatika.jwd.modul3.bioskop.web.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftninformatika.jwd.modul3.bioskop.model.Film;
import com.ftninformatika.jwd.modul3.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul3.bioskop.service.FilmService;
import com.ftninformatika.jwd.modul3.bioskop.service.ProjekcijaService;
import com.ftninformatika.jwd.modul3.bioskop.support.FilmDtoToFilm;
import com.ftninformatika.jwd.modul3.bioskop.support.FilmToFilmDTO;
import com.ftninformatika.jwd.modul3.bioskop.support.ProjekcijaToProjekcijaDTO;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.FilmDTO;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ProjekcijaDTO;


@RestController
@RequestMapping(value = "/api/filmovi", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class FilmoviController {

	@Autowired
    private FilmService filmService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private FilmDtoToFilm toFilm;

    @Autowired
    private FilmToFilmDTO toFilmDto;

    @Autowired
    private ProjekcijaToProjekcijaDTO toProjekcijaDto;
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> create(@Valid @RequestBody FilmDTO filmDTO){
        Film film = toFilm.convert(filmDTO);
        Film sacuvanFilm = filmService.save(film);

        return new ResponseEntity<>(toFilmDto.convert(sacuvanFilm), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> update(@PathVariable Long id, @Valid @RequestBody FilmDTO filmDTO){

        if(!id.equals(filmDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Film film = toFilm.convert(filmDTO);
        Film sacuvanFilm = filmService.update(film);

        return new ResponseEntity<>(toFilmDto.convert(sacuvanFilm),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Film obrisanFilm = filmService.delete(id);

        if(obrisanFilm != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getOne(@PathVariable Long id){
       Film film = filmService.findOne(id);

        if(film != null) {
            return new ResponseEntity<>(toFilmDto.convert(film), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAll(
            @RequestParam(required=false) String naziv,
            @RequestParam(required=false) Long zanrId,
            @RequestParam(required=false) Integer trajanjeOd,
            @RequestParam(required=false) Integer trajanjeDo){

        List<Film> filmovi = filmService.find(naziv,zanrId,trajanjeOd,trajanjeDo);

        return new ResponseEntity<>(toFilmDto.convert(filmovi), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
    @GetMapping("/{id}/projekcije")
    public ResponseEntity<List<ProjekcijaDTO>> findByFilmId(@PathVariable @Positive(message = "Id mora biti pozitivan.")  Long id){
        List<Projekcija> projekcije = projekcijaService.findByFilmId(id);

        return new ResponseEntity<>(toProjekcijaDto.convert(projekcije), HttpStatus.OK);
    }
}
