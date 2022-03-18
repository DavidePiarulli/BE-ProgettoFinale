package it.epicode.be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.common.util.exception.ServiceException;
import it.epicode.be.model.Indirizzo;
import it.epicode.be.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzoRepository;

	public Optional<Indirizzo> findById(Long id) {
		return indirizzoRepository.findById(id);
	}

	public Page<Indirizzo> findAll(Pageable pageable) {
		return indirizzoRepository.findAll(pageable);
	}

	public Indirizzo save(Indirizzo indirizzo) {
		return indirizzoRepository.save(indirizzo);
	}

	public Indirizzo update(Long id, Indirizzo indirizzo) {
		Optional<Indirizzo> indirizzoResult = indirizzoRepository.findById(id);

		if (indirizzoResult.isPresent()) {
			Indirizzo indirizzoUpdate = indirizzoResult.get();
			indirizzoUpdate.setCap(indirizzo.getCap());
			indirizzoUpdate.setCivico(indirizzo.getCivico());
			indirizzoUpdate.setLocalita(indirizzo.getLocalita());
			indirizzoUpdate.setTipo(indirizzo.getTipo());
			indirizzoUpdate.setVia(indirizzo.getVia());

			return indirizzoRepository.save(indirizzoUpdate);
		} else {
			throw new ServiceException("Address update unsuccessful");
		}
	}

	public void delete(long id) {
		indirizzoRepository.deleteById(id);
	}

	public List<Indirizzo> findAll() {
		return indirizzoRepository.findAll();
	}
}
