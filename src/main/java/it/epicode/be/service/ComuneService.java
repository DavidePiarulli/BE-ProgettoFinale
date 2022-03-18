package it.epicode.be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.model.Comune;
import it.epicode.be.repository.ComuneRepository;

@Service
public class ComuneService {

	@Autowired
	ComuneRepository comuneRepository;

	public Optional<Comune> findById(Long id) {
		return comuneRepository.findById(id);
	}

	public List<Comune> findByNome(String nome) {
		return comuneRepository.findByNomeStartingWithIgnoreCase(nome);
	}

	public Page<Comune> findAll(Pageable pageable) {
		return comuneRepository.findAll(pageable);
	}

	public List<Comune> findAll() {
		return comuneRepository.findAll();
	}

}
