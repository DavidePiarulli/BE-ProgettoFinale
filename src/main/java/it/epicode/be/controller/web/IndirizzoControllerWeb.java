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

import it.epicode.be.model.Indirizzo;
import it.epicode.be.service.ClienteService;
import it.epicode.be.service.ComuneService;
import it.epicode.be.service.IndirizzoService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class IndirizzoControllerWeb {

	@Autowired
	IndirizzoService indirizzoService;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ComuneService comuneService;

	@GetMapping("/indirizzi/mostraElenco")
	public ModelAndView mostraElenco(Pageable pageable, @RequestParam(defaultValue = "0") int page) {
		log.info("Test elenco indirizzi");
		ModelAndView view = new ModelAndView("elenco-indirizzi");
		view.addObject("indirizzi", indirizzoService.findAll(PageRequest.of(page, 20, Sort.by("cap"))));
		view.addObject("currentPage", page);
		return view;
	}

	@GetMapping("/indirizzi/mostraFormAggiungi")
	public String mostraFormAggiungi(Indirizzo indirizzo, Model model) {
		model.addAttribute("clienti", clienteService.findAll());
		model.addAttribute("comuni", comuneService.findAll());
		log.info("Test form aggiungi indirizzo");
		return "aggiungi-indirizzo";
	}

	@PostMapping("/indirizzi/aggiungi")
	public String aggiungi(Indirizzo indirizzo, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("clienti", clienteService.findAll());
			model.addAttribute("comuni", comuneService.findAll());
			return "aggiungi-studente";
		}
		indirizzoService.save(indirizzo);
		log.info("Test aggiungi indirizzo");
		return "redirect:/web/indirizzi/mostraElenco";
	}

	@GetMapping("/indirizzi/mostraFormAggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Test form agggiorna indirizzo");

		Optional<Indirizzo> studTemp = indirizzoService.findById(id);
		if (studTemp.isPresent()) {
			ModelAndView view = new ModelAndView("aggiorna-indirizzo");
			view.addObject("indirizzo", studTemp.get());
			view.addObject("cliente", clienteService.findAll());
			view.addObject("comune", comuneService.findAll());
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}

	@PostMapping("/indirizzi/aggiorna/{id}")
	public String mostraFormAggiungi(@PathVariable Long id, Indirizzo indirizzo, BindingResult result, Model model) {
		indirizzoService.update(id, indirizzo);
		log.info("Test aggiorna indirizzo");
		return "redirect:/web/indirizzi/mostraElenco";
	}

	@GetMapping("/indirizzi/elimina/{id}")
	public ModelAndView cancella(@PathVariable Long id, Model model) {
		Optional<Indirizzo> studTemp = indirizzoService.findById(id);
		if (studTemp.isPresent()) {
			log.info("Test cancella studente");
			indirizzoService.delete(studTemp.get().getId());
			return new ModelAndView("elenco-indirizzi");
		} else {
			return new ModelAndView("error").addObject("message", "id non trovato");
		}

	}

}
