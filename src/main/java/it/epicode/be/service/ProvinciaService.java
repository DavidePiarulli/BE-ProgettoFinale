package it.epicode.be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.model.Provincia;
import it.epicode.be.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	ProvinciaRepository provinciaRepository;

	public Optional<Provincia> findById(Long id) {
		return provinciaRepository.findById(id);
	}

	public List<Provincia> findByNome(String nome) {
		return provinciaRepository.findByNomeStartingWithIgnoreCase(nome);
	}

	public Page<Provincia> findAll(Pageable pageable) {
		return provinciaRepository.findAll(pageable);
	}

}
