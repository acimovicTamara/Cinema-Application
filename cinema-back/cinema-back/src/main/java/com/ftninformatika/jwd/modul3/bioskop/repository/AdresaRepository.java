package com.ftninformatika.jwd.modul3.bioskop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftninformatika.jwd.modul3.bioskop.model.Adresa;


@Repository
public interface AdresaRepository extends JpaRepository<Adresa, Long> {

}
