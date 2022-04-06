package it.epicode.be.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicode.be.model.Comune;
import it.epicode.be.model.Provincia;
import it.epicode.be.service.ComuneService;
import it.epicode.be.service.ProvinciaService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class ComuneControllerWeb {

	@Autowired
	ComuneService comuneService;

	@Autowired
	ProvinciaService provinciaService;

	@GetMapping("/comuni/page/{pageNo}")
	public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo) {
		log.info("Test elenco comuni");
		int pageSize = 100;
		Page<Comune> page = comuneService.findPaginated(pageNo, pageSize);
		List<Comune> listaComuni = page.getContent();
		ModelAndView view = new ModelAndView("elenco-comuni");
		view.addObject("comuni", page);
		view.addObject("currentPage", pageNo);
		view.addObject("totalPages", page.getTotalPages());
		view.addObject("totalItems", page.getTotalElements());
		view.addObject("listaComuni", listaComuni);
		return view;
	}

	@GetMapping("/comuni/mostraElenco")
	public ModelAndView mostraElenco() {
		return findPaginated(1);
	}

	@GetMapping("/comuni/load")
	public String loadData() {
		List<Provincia> province = provinciaService.initProvincia();
		comuneService.initComune(province);
		return "redirect:/web/comuni/mostraElenco";
	}

}
