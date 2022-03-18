package it.epicode.be.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import it.epicode.be.common.util.exception.ServiceException;
import it.epicode.be.model.Cliente;
import it.epicode.be.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	public Optional<Cliente> findByInitialNameOrderByFatturato(@RequestParam String prefix) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByFatturatoAnnuale(prefix);
	}

	public Optional<Cliente> findByInitialNameOrderByDataInserimento(@RequestParam String prefix) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByDataInserimento(prefix);
	}

	public Optional<Cliente> findByInitialNameOrderByDataUltimoContatto(@RequestParam String prefix) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByDataUltimoContatto(prefix);
	}

	public Optional<Cliente> findByInitialNameOrderByProvincia(@RequestParam String prefix) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByIndirizziComuneProvinciaNome(prefix);
	}

	public Optional<Cliente> findByFatturato(@RequestParam Double fatturato) {
		return clienteRepository.findByQueryParam(fatturato);
	}

	public Optional<Cliente> findByDataInserimento(@RequestParam LocalDate dataInserimento) {
		return clienteRepository.findByQueryParamInserimento(dataInserimento);
	}

	public Optional<Cliente> findByDataUltimoContatto(@RequestParam LocalDate dataUltimoContatto) {
		return clienteRepository.findByQueryParamUltimoContatto(dataUltimoContatto);
	}

	public Cliente save(Cliente cliente) {
		cliente.setDataInserimento(LocalDate.now());
		return clienteRepository.save(cliente);
	}

	public Cliente update(Long id, Cliente cliente) {
		Optional<Cliente> clienteResult = clienteRepository.findById(id);

		if (clienteResult.isPresent()) {
			Cliente clienteUpdate = clienteResult.get();
			clienteUpdate.setRagioneSociale(cliente.getRagioneSociale());
			clienteUpdate.setPartitaIva(cliente.getPartitaIva());
			clienteUpdate.setEmail(cliente.getEmail());
			clienteUpdate.setDataInserimento(LocalDate.now());
			clienteUpdate.setDataUltimoContatto(cliente.getDataUltimoContatto());
			clienteUpdate.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
			clienteUpdate.setPec(cliente.getPec());
			clienteUpdate.setTelefono(cliente.getTelefono());
			clienteUpdate.setEmailContatto(cliente.getEmailContatto());
			clienteUpdate.setNomeContatto(cliente.getNomeContatto());
			clienteUpdate.setCognomeContatto(cliente.getCognomeContatto());
			clienteUpdate.setTelefonoContatto(cliente.getTelefonoContatto());
			clienteUpdate.setTipo(cliente.getTipo());
			return clienteRepository.save(clienteUpdate);
		} else {
			throw new ServiceException("Client update unsuccessful");
		}
	}

	public void delete(long id) {
		clienteRepository.deleteById(id);
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

}
