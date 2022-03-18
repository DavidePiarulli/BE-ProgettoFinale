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
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	IndirizzoService indirizzoService;

	@Autowired
	private ComuneService comuneService;

	@GetMapping("/clienti")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Page<Cliente>> findAll(Pageable pageable) {
		Page<Cliente> findAll = clienteService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Page<Cliente>) null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/clienti/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Cliente> findById(@PathVariable(required = true) Long id) {
		Optional<Cliente> find = clienteService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Cliente) null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/cliente")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
		Cliente save = clienteService.save(cliente);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@PostMapping("/cliente/indirizzo/comune/{comuneId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Cliente> saveWithIndirizzo(@RequestBody Cliente cliente,
			@PathVariable(required = true, value = "comuneId") Long comuneId) {
		Optional<Comune> find = comuneService.findById(comuneId);
		if (find.isPresent()) {
			Indirizzo i = cliente.getIndirizzi().get(0).addComune(find.get());
			cliente.addIndirizzo(i);
			Cliente save = clienteService.save(cliente);
			indirizzoService.save(i);
			return new ResponseEntity<>(save, HttpStatus.OK);

		}

		return new ResponseEntity<>((Cliente) null, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/cliente/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Cliente> update(@PathVariable(required = true) Long id, @RequestBody Cliente cliente) {
		Cliente update = clienteService.update(id, cliente);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

	@DeleteMapping("/cliente/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable(required = true) Long id) {
		clienteService.delete(id);
		return new ResponseEntity<>("Client deleted", HttpStatus.OK);
	}

}
