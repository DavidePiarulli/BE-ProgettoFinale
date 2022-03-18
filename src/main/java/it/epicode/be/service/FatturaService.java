package it.epicode.be.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import it.epicode.be.common.util.exception.ServiceException;
import it.epicode.be.model.Fattura;
import it.epicode.be.repository.FatturaRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturaRepository;

	public Optional<Fattura> findById(Long id) {
		return fatturaRepository.findById(id);
	}

	public Page<Fattura> findAll(Pageable pageable) {
		return fatturaRepository.findAll(pageable);
	}

	public Optional<Fattura> findByCliente(@RequestParam Long clienteId) {
		return fatturaRepository.findByQueryParam(clienteId);
	}

	public Optional<Fattura> findByStato(@RequestParam String stato) {
		return fatturaRepository.findByQueryParamStato(stato);
	}

	public Optional<Fattura> findByData(@RequestParam Date date) {
		return fatturaRepository.findByQueryParamDate(date);
	}

	public Optional<Fattura> findByAnno(@RequestParam Integer anno) {
		return fatturaRepository.findByQueryParamAnno(anno);
	}

	public Optional<Fattura> findByImporto(@RequestParam BigDecimal importo) {
		return fatturaRepository.findByQueryParamImporto(importo);
	}

	public Fattura save(Fattura fattura) {
		return fatturaRepository.save(fattura);
	}

	public Fattura update(Long id, Fattura fattura) {
		Optional<Fattura> fatturaResult = fatturaRepository.findById(id);

		if (fatturaResult.isPresent()) {
			Fattura fatturaUpdate = fatturaResult.get();
			fatturaUpdate.setAnno(fattura.getAnno());
			fatturaUpdate.setImporto(fattura.getImporto());
			fatturaUpdate.setNumero(fattura.getNumero());
			fatturaUpdate.setStato(fattura.getStato());

			return fatturaRepository.save(fatturaUpdate);
		} else {
			throw new ServiceException("Invoice update unsuccessful");
		}
	}

	public void delete(long id) {
		fatturaRepository.deleteById(id);
	}

}
