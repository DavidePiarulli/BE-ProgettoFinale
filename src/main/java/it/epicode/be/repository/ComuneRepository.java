package it.epicode.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.model.Comune;

public interface ComuneRepository extends JpaRepository<Comune, Long> {

	public List<Comune> findByNomeStartingWithIgnoreCase(String nome);

}
