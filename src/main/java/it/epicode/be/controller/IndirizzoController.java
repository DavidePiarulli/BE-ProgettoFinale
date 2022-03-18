package it.epicode.be.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.model.Cliente;
import it.epicode.be.model.Comune;
import it.epicode.be.model.Indirizzo;
import it.epicode.be.service.ClienteService;
import it.epicode.be.service.ComuneService;
import it.epicode.be.service.IndirizzoService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class IndirizzoController {

	@Autowired
	ClienteService clienteService;

	@Autowired
	IndirizzoService indirizzoService;

	@Autowired
	ComuneService comuneService;

	@GetMapping("/indirizzi")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Page<Indirizzo>> findAll(Pageable pageable) {
		Page<Indirizzo> findAll = indirizzoService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Page<Indirizzo>) null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/indirizzi/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Indirizzo> findById(@PathVariable(required = true) Long id) {
		Optional<Indirizzo> find = indirizzoService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Indirizzo) null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/clienti/{clienteId}/indirizzo/comune/{comuneId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Indirizzo> create(@PathVariable(value = "clienteId", required = true) Long clienteId,
			@RequestBody Indirizzo i, @PathVariable(value = "comuneId", required = true) Long comuneId) {
		Optional<Cliente> findCliente = clienteService.findById(clienteId);
		Optional<Comune> findComune = comuneService.findById(comuneId);
		Indirizzo indirizzo = new Indirizzo();
		indirizzo.setCap(i.getCap());
		indirizzo.setCivico(i.getCivico());
		indirizzo.setLocalita(i.getLocalita());
		indirizzo.setTipo(i.getTipo());
		indirizzo.setVia(i.getVia());

		if (findComune.isPresent()) {
			indirizzo.addComune(findComune.get());

			if (findCliente.isPresent()) {
				clienteService.save(findCliente.get().addIndirizzo(indirizzo));
				Indirizzo saveIndirizzo = indirizzoService.save(indirizzo);
				return new ResponseEntity<>(saveIndirizzo, HttpStatus.CREATED);
			}
		}

		return new ResponseEntity<>((Indirizzo) null, HttpStatus.NOT_FOUND);

	}

	@PutMapping("/indirizzo/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Indirizzo> update(@PathVariable(required = true) Long id, @RequestBody Indirizzo indirizzo) {
		Indirizzo update = indirizzoService.update(id, indirizzo);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

	@PutMapping("/indirizzo/{indirizzoId}/comune/{comuneId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Indirizzo> updateToDifferentComune(
			@PathVariable(required = true, value = "indirizzoId") Long indirizzoId, @RequestBody Indirizzo i,
			@PathVariable(required = true, value = "comuneId") Long comuneId) {
		Optional<Comune> findComune = comuneService.findById(comuneId);

		if (findComune.isPresent()) {
			if (indirizzoService.findById(indirizzoId).isPresent()) {

				indirizzoService.findById(indirizzoId).get().addComune(findComune.get());
			}
		}
		Indirizzo update = indirizzoService.update(indirizzoId, i);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

	@DeleteMapping("/cliente/{clienteId}/indirizzo/{indirizzoId}/comune/{comuneId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable(value = "clienteId", required = true) Long clienteId,
			@PathVariable(value = "indirizzoId", required = true) Long indirizzoId,
			@PathVariable(value = "comuneId", required = true) Long comuneId) {
		Optional<Cliente> findCliente = clienteService.findById(clienteId);
		Optional<Comune> findComune = comuneService.findById(comuneId);
		if (findComune.isPresent()) {
			if (indirizzoService.findById(indirizzoId).isPresent()) {
				indirizzoService.findById(indirizzoId).get().deleteComune(comuneId);

			}
			if (findCliente.isPresent()) {
				clienteService.save(findCliente.get().deleteIndirizzo(indirizzoId));
				indirizzoService.delete(indirizzoId);

				return new ResponseEntity<>("Address deleted", HttpStatus.OK);
			}
		}

		else {
			return new ResponseEntity<>("Incorrect ID supplied", HttpStatus.NOT_FOUND);
		}
		return null;

	}

}
