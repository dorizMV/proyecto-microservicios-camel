package com.pasportes.validacion.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pasportes.validacion.entities.Persona;

public interface PersonaRepository extends MongoRepository<Persona, String> {
	
	public Optional<Persona> findByName(String name);

}
