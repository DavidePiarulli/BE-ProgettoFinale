package it.epicode.be.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.epicode.be.service.ProvinciaService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class ProvinciaControllerWeb {

	@Autowired
	ProvinciaService provinciaService;

	@GetMapping("/province/mostraElenco")
	public ModelAndView mostraElenco(Pageable pageable, @RequestParam(defaultValue = "0") int page) {
		log.info("Test elenco province");
		ModelAndView view = new ModelAndView("elenco-province");
		view.addObject("province", provinciaService.findAll(PageRequest.of(page, 50, Sort.by("nome"))));
		view.addObject("currentPage", page);
		return view;
	}

}
