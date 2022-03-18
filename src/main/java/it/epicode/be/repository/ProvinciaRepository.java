package it.epicode.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.model.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	public List<Provincia> findByNomeStartingWithIgnoreCase(String nome);

	public Optional<Provincia> findByNome(String nome);

}
