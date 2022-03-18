package it.epicode.be.controller.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.epicode.be.model.Fattura;
import it.epicode.be.service.ClienteService;
import it.epicode.be.service.FatturaService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class FattureControllerWeb {

	@Autowired
	FatturaService fatturaService;

	@Autowired
	ClienteService clienteService;

	@GetMapping("/fatture/mostraElenco")
	public ModelAndView mostraElenco(Pageable pageable, @RequestParam(defaultValue = "0") int page) {
		log.info("Test elenco fatture");
		ModelAndView view = new ModelAndView("elenco-fatture");
		view.addObject("fatture", fatturaService.findAll(PageRequest.of(page, 20, Sort.by("cliente"))));
		view.addObject("currentPage", page);
		return view;
	}

	@GetMapping("/fatture/mostraFormAggiungi")
	public String mostraFormAggiungi(Fattura fattura, Model model) {
		model.addAttribute("clienti", clienteService.findAll());
		log.info("Test form aggiungi fattura");
		return "aggiungi-fattura";
	}

	@PostMapping("/fatture/aggiungi")
	public String aggiungi(Fattura fattura, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("clienti", clienteService.findAll());
			return "aggiungi-fattura";
		}
		fatturaService.save(fattura);
		log.info("Test aggiungi fattura");
		return "redirect:/web/fatture/mostraElenco";
	}

	@GetMapping("/fatture/mostraFormAggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Test form agggiorna fattura");

		Optional<Fattura> fattTemp = fatturaService.findById(id);
		if (fattTemp.isPresent()) {
			ModelAndView view = new ModelAndView("aggiorna-studente");
			view.addObject("fattura", fattTemp.get());
			view.addObject("clienti", clienteService.findAll());
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}

	@PostMapping("/fatture/aggiorna/{id}")
	public String mostraFormAggiungi(@PathVariable Long id, Fattura fattura, BindingResult result, Model model) {
		fatturaService.update(id, fattura);
		log.info("Test aggiorna fattura");
		return "redirect:/web/fatture/mostraElenco";
	}

	@GetMapping("/fatture/elimina/{id}")
	public ModelAndView cancella(@PathVariable Long id, Model model) {
		Optional<Fattura> fattTemp = fatturaService.findById(id);
		if (fattTemp.isPresent()) {
			log.info("Test cancella fattura");
			fatturaService.delete(fattTemp.get().getId());
			return new ModelAndView("elenco-fatture");
		} else {
			return new ModelAndView("error").addObject("message", "id non trovato");
		}

	}
}
