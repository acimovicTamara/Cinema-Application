package com.ftninformatika.jwd.modul3.bioskop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftninformatika.jwd.modul3.bioskop.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>{

	Film findOneById(Long id);
	
	List<Film> findByZanroviId(Long zanrId);
	
	List<Film> findByNazivIgnoreCaseContainsAndTrajanjeBetweenAndZanroviId(String naziv, Integer trajanjeOd, Integer trajanjeDo, Long zanrId);
	
    List<Film> findByNazivIgnoreCaseContainsAndTrajanjeBetween(String naziv,Integer trajanjeOd,Integer trajanjeDo);

}
