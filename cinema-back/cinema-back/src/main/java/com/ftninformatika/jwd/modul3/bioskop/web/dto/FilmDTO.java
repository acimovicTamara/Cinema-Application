package com.ftninformatika.jwd.modul3.bioskop.web.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class FilmDTO {

	@Positive(message = "Id mora biti pozitivan broj.")
	private Long id;
	
	@NotBlank(message = "Naziv filma nije zadat.")
	private String naziv;
	
	@NotNull(message = "Nije zadato trajanje filma.")
	@Positive(message = "Trajanje filma nije pozitivan broj.")
	private int trajanje;
	
	private Set<ZanrDTO> zanrovi = new HashSet<>();

	public FilmDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public Set<ZanrDTO> getZanrovi() {
		return zanrovi;
	}

	public void setZanrovi(Set<ZanrDTO> zanrovi) {
		this.zanrovi = zanrovi;
	}
	
	
}
