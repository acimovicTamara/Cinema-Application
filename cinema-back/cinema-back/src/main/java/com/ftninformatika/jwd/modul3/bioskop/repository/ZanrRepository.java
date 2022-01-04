package com.ftninformatika.jwd.modul3.bioskop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftninformatika.jwd.modul3.bioskop.model.Zanr;

@Repository
public interface ZanrRepository extends JpaRepository<Zanr, Long> {

	Zanr findOneById(Long id);

	List<Zanr> findByNazivIgnoreCaseContains(String naziv);

	List<Zanr> findByIdIn(List<Long> ids);
}
