package com.ftninformatika.jwd.modul3.bioskop.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.ftninformatika.jwd.modul3.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul3.bioskop.service.FilmService;
import com.ftninformatika.jwd.modul3.bioskop.service.ZanrService;
import com.ftninformatika.jwd.modul3.bioskop.support.FilmToFilmDTO;
import com.ftninformatika.jwd.modul3.bioskop.support.ZanrDtoToZanr;
import com.ftninformatika.jwd.modul3.bioskop.support.ZanrToZanrDTO;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.FilmDTO;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ZanrDTO;

@RestController
@RequestMapping(value = "/api/zanrovi", produces = MediaType.APPLICATION_JSON_VALUE)
public class ZanrController {

	@Autowired
	private ZanrService zanrService;

	@Autowired
	private FilmService filmService;

	@Autowired
	private ZanrToZanrDTO toZanrDto;

	@Autowired
	private ZanrDtoToZanr toZanr;

	@Autowired
	private FilmToFilmDTO toFilmDto;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZanrDTO> create(@Valid @RequestBody ZanrDTO zanrDTO) {
		Zanr zanr = toZanr.convert(zanrDTO);
		Zanr sacuvanZanr = zanrService.save(zanr);

		return new ResponseEntity<>(toZanrDto.convert(sacuvanZanr), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZanrDTO> update(@PathVariable Long id, @Valid @RequestBody ZanrDTO zanrDTO) {

		if (!id.equals(zanrDTO.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Zanr zanr = toZanr.convert(zanrDTO);
		Zanr sacuvanZanr = zanrService.update(zanr);

		return new ResponseEntity<>(toZanrDto.convert(sacuvanZanr), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Zanr obrisanZanr = zanrService.delete(id);

		if (obrisanZanr != null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ZanrDTO> getOne(@PathVariable Long id) {
		Zanr zanr = zanrService.findOne(id);

		if (zanr != null) {
			return new ResponseEntity<>(toZanrDto.convert(zanr), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<ZanrDTO>> getAll(@RequestParam(required = false) String naziv) {

		List<Zanr> zanrovi;

		if (naziv == null) {
			zanrovi = zanrService.findAll();
		} else {
			zanrovi = zanrService.find(naziv);
		}

		return new ResponseEntity<>(toZanrDto.convert(zanrovi), HttpStatus.OK);
	}

	@GetMapping("/{id}/filmovi")
	public ResponseEntity<List<FilmDTO>> findByZanrId(@PathVariable Long id) {
		List<Film> filmovi = filmService.findByZanrId(id);

		return new ResponseEntity<>(toFilmDto.convert(filmovi), HttpStatus.OK);
	}
}
