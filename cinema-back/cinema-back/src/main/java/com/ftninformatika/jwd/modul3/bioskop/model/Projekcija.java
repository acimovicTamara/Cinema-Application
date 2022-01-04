package com.ftninformatika.jwd.modul3.bioskop.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Projekcija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "datum_vreme", nullable = false)
	private LocalDateTime datumIVreme;

	@Column
	private int sala;

	@Column
	private String tip;

	@Column
	private double cenaKarte;

	@ManyToOne
	private Film film;

	public Projekcija() {
		super();

	}

	public Projekcija(LocalDateTime datumIVreme, int sala, String tip, double cenaKarte, Film film) {
		this.datumIVreme = datumIVreme;
		this.sala = sala;
		this.tip = tip;
		this.cenaKarte = cenaKarte;
		this.film = film;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDatumIVreme() {
		return datumIVreme;
	}

	public void setDatumIVreme(LocalDateTime datumIVreme) {
		this.datumIVreme = datumIVreme;
	}

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public double getCenaKarte() {
		return cenaKarte;
	}

	public void setCenaKarte(double cenaKarte) {
		this.cenaKarte = cenaKarte;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
		if (film != null && !film.getProjekcije().contains(this)) {
			film.getProjekcije().add(this);
		}

	}

	@Override
	public String toString() {
		return "Projekcija [id=" + id + ", datumIVreme=" + datumIVreme + ", sala=" + sala + ", tip=" + tip
				+ ", cenaKarte=" + cenaKarte + ", film=" + film + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projekcija other = (Projekcija) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
