package it.epicode.be.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.model.Provincia;
import it.epicode.be.service.ProvinciaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ProvinciaController {

	@Autowired
	ProvinciaService provinciaService;

	@GetMapping("/province")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Page<Provincia>> findAll(Pageable pageable) {
		Page<Provincia> findAll = provinciaService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Page<Provincia>) null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/province/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Provincia> findById(@PathVariable(required = true) Long id) {
		Optional<Provincia> find = provinciaService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>((Provincia) null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/province/nome/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<Provincia>> findByNome(@PathVariable(required = true) String nome) {
		List<Provincia> find = provinciaService.findByNome(nome);

		if (!find.isEmpty()) {
			return new ResponseEntity<>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<>((List<Provincia>) null, HttpStatus.NOT_FOUND);
		}
	}

}
