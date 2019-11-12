package com.pasportes.validacion.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pasportes.validacion.entities.Pais;

public interface PaisRepository extends MongoRepository<Pais, String> {
	public Optional<Pais> findByNombre(String nombre);
}
