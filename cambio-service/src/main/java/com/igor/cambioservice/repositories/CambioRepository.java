package com.igor.cambioservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igor.cambioservice.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long>{
	Cambio findByFromAndTo(String from, String to);
}
