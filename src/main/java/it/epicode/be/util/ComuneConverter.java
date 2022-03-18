package it.epicode.be.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import it.epicode.be.model.Comune;
import it.epicode.be.service.ComuneService;

public class ComuneConverter implements Converter<Long, Comune> {

	@Autowired
	ComuneService comuneService;

	@Override
	public Comune convert(Long source) {
		return comuneService.findById(source).get();
	}

}
