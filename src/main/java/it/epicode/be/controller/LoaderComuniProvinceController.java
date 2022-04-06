package it.epicode.be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.model.Provincia;
import it.epicode.be.service.ComuneService;
import it.epicode.be.service.ProvinciaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class LoaderComuniProvinceController {

	@Autowired
	ComuneService comuneService;

	@Autowired
	ProvinciaService provinciaService;

	@PostMapping("/loader/comuni/province")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> load() {
		List<Provincia> province = provinciaService.initProvincia();
		comuneService.initComune(province);
		return new ResponseEntity<>("Data loaded", HttpStatus.OK);
	}

}
