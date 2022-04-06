package it.epicode.be.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicode.be.model.Provincia;
import it.epicode.be.service.ProvinciaService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class ProvinciaControllerWeb {

	@Autowired
	ProvinciaService provinciaService;

	@GetMapping("/province/page/{pageNo}")
	public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo) {
		log.info("Test elenco province");
		int pageSize = 100;
		Page<Provincia> page = provinciaService.findPaginated(pageNo, pageSize);
		List<Provincia> listaProvince = page.getContent();
		ModelAndView view = new ModelAndView("elenco-province");
		view.addObject("province", page);
		view.addObject("currentPage", pageNo);
		view.addObject("totalPages", page.getTotalPages());
		view.addObject("totalItems", page.getTotalElements());
		view.addObject("listaProvince", listaProvince);
		return view;
	}

	@GetMapping("/province/mostraElenco")
	public ModelAndView mostraElenco() {
		return findPaginated(1);
	}

}
