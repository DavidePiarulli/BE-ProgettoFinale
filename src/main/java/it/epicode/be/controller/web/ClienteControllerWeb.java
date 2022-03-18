package it.epicode.be.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@GetMapping("/clienti/mostraElenco")
	public ModelAndView mostraElenco(Pageable pageable, @RequestParam(defaultValue = "0") int page) {
		log.info("Test elenco clienti");
		ModelAndView view = new ModelAndView("elenco-clienti");
		view.addObject("clienti", clienteService.findAll(PageRequest.of(page, 4, Sort.by("dataInserimento"))));
		view.addObject("currentPage", page);
		return view;
	}

	@GetMapping("/clienti/mostraFormAggiungi")
	public String mostraFormAggiungi(Cliente cliente, Model model, Pageable pageable) {
		model.addAttribute("indirizzi", clienteService.findAll(pageable));
		log.info("Test form aggiungi cliente");
		return "aggiungi-cliente";
	}

	@GetMapping("/findOne")
	@ResponseBody
	public Cliente findOne(Long id) {
		return clienteService.findById(id).get();
	}

	@GetMapping("/delete")
	public String delete(Long id) {
		clienteService.delete(id);
		return "redirect:/web/clienti/mostraElenco";
	}

	@PostMapping("/clienti/aggiungi")
	public String aggiungi(Cliente cliente, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "aggiungi-cliente";
		}
		clienteService.save(cliente);
		log.info("Test aggiungi Cliente");
		return "redirect:/web/clienti/mostraElenco";
	}

}
