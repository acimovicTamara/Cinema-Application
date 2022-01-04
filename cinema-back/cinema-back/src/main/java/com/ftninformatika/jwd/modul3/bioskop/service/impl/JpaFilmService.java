package com.ftninformatika.jwd.modul3.bioskop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftninformatika.jwd.modul3.bioskop.model.Film;
import com.ftninformatika.jwd.modul3.bioskop.repository.FilmRepository;
import com.ftninformatika.jwd.modul3.bioskop.service.FilmService;

@Service
public class JpaFilmService implements FilmService {

	@Autowired
	private FilmRepository filmRepository;

	@Override
	public Film findOne(Long id) {
		// TODO Auto-generated method stub
		return filmRepository.findOneById(id);
	}

	@Override
	public List<Film> findAll() {
		// TODO Auto-generated method stub
		return filmRepository.findAll();
	}

	@Override
	public Film save(Film film) {
		// TODO Auto-generated method stub
		return filmRepository.save(film);
	}

	@Override
	public Film update(Film film) {
		return filmRepository.save(film);
	}

	@Override
	public Film delete(Long id) {
		Optional<Film> film = filmRepository.findById(id);
		if (film.isPresent()) {
			filmRepository.deleteById(id);
			return film.get();
		}
		return null;
	}

	@Override
	public List<Film> find(String naziv, Long zanrId, Integer trajanjeOd, Integer trajanjeDo) {
		if (naziv == null) {
            naziv = "";
        }

        if (trajanjeOd == null) {
            trajanjeOd = 0;
        }

        if (trajanjeDo == null) {
            trajanjeDo = Integer.MAX_VALUE;
        }

        if(zanrId == null){
            return filmRepository.findByNazivIgnoreCaseContainsAndTrajanjeBetween(naziv,trajanjeOd,trajanjeDo);
        }
        return filmRepository.findByNazivIgnoreCaseContainsAndTrajanjeBetweenAndZanroviId(naziv,trajanjeOd,trajanjeDo,zanrId);
	}

	@Override
	public List<Film> findByZanrId(Long zanrId) {
        return filmRepository.findByZanroviId(zanrId);

	}

}
