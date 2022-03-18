package it.epicode.be.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import it.epicode.be.model.Cliente;
import it.epicode.be.service.ClienteService;

public class ClienteConverter implements Converter<Long, Cliente> {

	@Autowired
	ClienteService clienteService;

	@Override
	public Cliente convert(Long id) {
		return clienteService.findById(id).get();
	}

}
