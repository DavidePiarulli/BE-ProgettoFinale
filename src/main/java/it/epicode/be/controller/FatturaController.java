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
import it.epicode.be.model.Fattura;
import it.epicode.be.service.ClienteService;
import it.epicode.be.service.FatturaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class FatturaController {
	@Autowired
	ClienteService clienteService;

	@Autowired
	FatturaService fatturaService;

	@GetMapping("/fatture")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Page<Fattura>> findAll(Pageable pageable) {
		Page<Fattura> findAll = fatturaService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Page<Fattura>) null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/fatture/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Fattura> findById(@PathVariable(required = true) Long id) {
		Optional<Fattura> find = fatturaService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Fattura) null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/clienti/{clienteId}/fatture")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Fattura> create(@PathVariable(value = "clienteId", required = true) Long clienteId,
			@RequestBody Fattura f) {
		Optional<Cliente> findCliente = clienteService.findById(clienteId);
		if (findCliente.isPresent()) {
			clienteService.save(findCliente.get().addFattura(f));
			Fattura saveFattura = fatturaService.save(f);

			return new ResponseEntity<>(saveFattura, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>((Fattura) null, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/fattura/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Fattura> update(@PathVariable(required = true) Long id, @RequestBody Fattura fattura) {

		Fattura update = fatturaService.update(id, fattura);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

	@DeleteMapping("/clienti/{clienteId}/fatture/{fatturaId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable(value = "clienteId", required = true) Long clienteId,
			@PathVariable(value = "fatturaId", required = true) Long fatturaId) {
		Optional<Cliente> findCliente = clienteService.findById(clienteId);
		if (findCliente.isPresent()) {
			clienteService.save(findCliente.get().deleteFattura(fatturaId));
			fatturaService.delete(fatturaId);
			return new ResponseEntity<>("Invoice deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Incorrect ID supplied", HttpStatus.NOT_FOUND);
		}

	}

}
