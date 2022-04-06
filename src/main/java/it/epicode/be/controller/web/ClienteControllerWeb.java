package it.epicode.be.controller.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.epicode.be.model.Cliente;
import it.epicode.be.service.ClienteService;
import it.epicode.be.service.IndirizzoService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class ClienteControllerWeb {

	@Autowired
	ClienteService clienteService;

	@Autowired
	IndirizzoService indirizzoService;

	@GetMapping("/clienti/page/{pageNo}")
	public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
		log.info("Test elenco clienti");
		int pageSize = 2;
		Page<Cliente> page = clienteService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Cliente> listaClienti = page.getContent();
		ModelAndView view = new ModelAndView("elenco-clienti");
		view.addObject("clienti", page);
		view.addObject("currentPage", pageNo);
		view.addObject("totalPages", page.getTotalPages());
		view.addObject("totalItems", page.getTotalElements());
		view.addObject("sortField", sortField);
		view.addObject("sortDir", sortDir);
		view.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		view.addObject("listaClienti", listaClienti);
		return view;
	}

	@GetMapping("/clienti/mostraElenco")
	public ModelAndView mostraElenco() {
		return findPaginated(1, "ragioneSociale", "asc");
	}

	@GetMapping("/clienti/mostraFormAggiungi")
	public String mostraFormAggiungi(Cliente cliente, Model model, Pageable pageable) {
		model.addAttribute("tipi", clienteService.findAll());
		log.info("Test form aggiungi cliente");
		return "aggiungi-cliente";
	}

	@PostMapping("/clienti/aggiungi")
	public String aggiungi(Cliente cliente, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("tipi", clienteService.findAll());
			return "aggiungi-cliente";
		}
		clienteService.save(cliente);
		log.info("Test aggiungi Cliente");
		return "redirect:/web/clienti/mostraElenco";
	}

	@GetMapping("/clienti/mostraFormAggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Test form agggiorna cliente");

		Optional<Cliente> clienteTemp = clienteService.findById(id);
		if (clienteTemp.isPresent()) {
			ModelAndView view = new ModelAndView("aggiorna-cliente");
			view.addObject("cliente", clienteTemp.get());
			view.addObject("indirizzi", clienteService.findAll());
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}

	@PostMapping("/clienti/aggiorna/{id}")
	public String mostraFormAggiungi(@PathVariable Long id, Cliente cliente, BindingResult result, Model model) {
		clienteService.update(id, cliente);
		log.info("Test aggiorna cliente");
		return "redirect:/web/clienti/mostraElenco";
	}

	@GetMapping("/clienti/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, Model model) {
		Optional<Cliente> clienteTemp = clienteService.findById(id);
		if (clienteTemp.isPresent()) {
			log.info("Test cancella cliente");
			clienteService.delete(clienteTemp.get().getId());
			return findPaginated(1, "ragioneSociale", "asc");
		} else {
			return new ModelAndView("error").addObject("message", "Something went wrong, please try again");

		}
	}

}
