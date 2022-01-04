package com.ftninformatika.jwd.modul3.bioskop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false) // obavezno polje
	private String naziv;

	@Column
	private int trajanje;

	@OneToMany(mappedBy = "film", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Projekcija> projekcije = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "film_zanr", joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "zanr_id", referencedColumnName = "id"))
	private Set<Zanr> zanrovi = new HashSet<>();

	public Film() {
		super();

	}

	public Film(String naziv, int trajanje) {
		this.naziv = naziv;
		this.trajanje = trajanje;
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

	public List<Projekcija> getProjekcije() {
		return projekcije;
	}

	public void setProjekcije(List<Projekcija> projekcije) {
		this.projekcije = projekcije;
	}

	public void dodajProjekciju(Projekcija projekcija) {
		this.projekcije.add(projekcija);
		if (!equals(projekcija.getFilm())) {
			projekcija.setFilm(this);
		}
	}

	public Set<Zanr> getZanrovi() {
		return zanrovi;
	}

	public void setZanrovi(Set<Zanr> zanrovi) {
		this.zanrovi = zanrovi;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", naziv=" + naziv + ", trajenje=" + trajanje + "]";
	}

}
