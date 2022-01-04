package com.ftninformatika.jwd.modul3.bioskop.web.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftninformatika.jwd.modul3.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul3.bioskop.service.ProjekcijaService;
import com.ftninformatika.jwd.modul3.bioskop.support.ProjekcijaDtoToProjekcija;
import com.ftninformatika.jwd.modul3.bioskop.support.ProjekcijaToProjekcijaDTO;
import com.ftninformatika.jwd.modul3.bioskop.web.dto.ProjekcijaDTO;

@RestController
@RequestMapping(value = "/api/projekcije", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjekcijaController {

	@Autowired
	private ProjekcijaService projekcijaService;

	@Autowired
	private ProjekcijaDtoToProjekcija toProjekcija;

	@Autowired
	private ProjekcijaToProjekcijaDTO toProjekcijaDto;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjekcijaDTO> create(@Valid @RequestBody ProjekcijaDTO projekcijaDTO) {
		Projekcija projekcija = toProjekcija.convert(projekcijaDTO);

		if (projekcija.getFilm() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Projekcija sacuvanProjekcija = projekcijaService.save(projekcija);

		return new ResponseEntity<>(toProjekcijaDto.convert(sacuvanProjekcija), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjekcijaDTO> update(@PathVariable Long id,
			@Valid @RequestBody ProjekcijaDTO projekcijaDTO) {

		if (!id.equals(projekcijaDTO.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Projekcija projekcija = toProjekcija.convert(projekcijaDTO);

		if (projekcija.getFilm() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Projekcija sacuvanProjekcija = projekcijaService.update(projekcija);

		return new ResponseEntity<>(toProjekcijaDto.convert(sacuvanProjekcija), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Projekcija obrisanProjekcija = projekcijaService.delete(id);

		if (obrisanProjekcija != null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ProjekcijaDTO> getOne(@PathVariable Long id) {
		Projekcija projekcija = projekcijaService.findOne(id);

		if (projekcija != null) {
			return new ResponseEntity<>(toProjekcijaDto.convert(projekcija), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
	@GetMapping
	public ResponseEntity<List<ProjekcijaDTO>> getAll(@RequestParam(required = false) String datumIVremeOdParametar,
			@RequestParam(required = false) String datumIVremeDoParametar, @RequestParam(required = false) Long filmId,
			@RequestParam(required = false) String tip, @RequestParam(required = false) Integer sala,
			@RequestParam(required = false) Double cenaKarteOd, @RequestParam(required = false) Double cenaKarteDo,
			@RequestParam(value = "pageNo", defaultValue = "0") int pageNo) {

		Page<Projekcija> page;

		if (datumIVremeOdParametar != null && datumIVremeDoParametar != null) {

			LocalDateTime datumIVremeOd = getLocalDateTime(datumIVremeOdParametar);
			LocalDateTime datumIVremeDo = getLocalDateTime(datumIVremeDoParametar);

			page = projekcijaService.find(datumIVremeOd, datumIVremeDo, filmId, tip, sala, cenaKarteOd, cenaKarteDo,
					pageNo);
		} else {
			page = projekcijaService.findAll(pageNo);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Total-Pages", Integer.toString(page.getTotalPages()));

		return new ResponseEntity<>(toProjekcijaDto.convert(page.getContent()), headers, HttpStatus.OK);
	}

	private LocalDateTime getLocalDateTime(String datumIVreme) throws DateTimeParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate datum = LocalDate.parse(datumIVreme.substring(0, 10), formatter);
		LocalTime vreme = LocalTime.parse(datumIVreme.substring(11), DateTimeFormatter.ofPattern("HH:mm"));
		return LocalDateTime.of(datum, vreme);
	}
}
