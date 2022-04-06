package it.epicode.be.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public Page<Cliente> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return clienteRepository.findAll(pageable);
	}

	public Page<Cliente> findByInitialNameOrderByFatturato(@RequestParam String prefix, Pageable pageable) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByFatturatoAnnuale(prefix, pageable);
	}

	public Page<Cliente> findByInitialNameOrderByDataInserimento(@RequestParam String prefix, Pageable pageable) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByDataInserimento(prefix, pageable);
	}

	public Page<Cliente> findByInitialNameOrderByDataUltimoContatto(@RequestParam String prefix, Pageable pageable) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByDataUltimoContatto(prefix, pageable);
	}

	public Page<Cliente> findByInitialNameOrderByProvincia(@RequestParam String prefix, Pageable pageable) {
		return clienteRepository.findByNomeContattoStartingWithIgnoreCaseOrderByIndirizziComuneProvinciaNome(prefix,
				pageable);
	}

	public Page<Cliente> findByFatturato(@RequestParam Double fatturato, Pageable pageable) {
		return clienteRepository.findByQueryParam(fatturato, pageable);
	}

	public Page<Cliente> findByDataInserimento(@RequestParam LocalDate dataInserimento, Pageable pageable) {
		return clienteRepository.findByQueryParamInserimento(dataInserimento, pageable);
	}

	public Page<Cliente> findByDataUltimoContatto(@RequestParam LocalDate dataUltimoContatto, Pageable pageable) {
		return clienteRepository.findByQueryParamUltimoContatto(dataUltimoContatto, pageable);
	}

	public Cliente save(Cliente cliente) {
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(LocalDate.now());
		return clienteRepository.save(cliente);
	}

	public Cliente update(Long id, Cliente cliente) {
		Optional<Cliente> clienteResult = clienteRepository.findById(id);

		if (clienteResult.isPresent()) {
			Cliente clienteUpdate = clienteResult.get();
			clienteUpdate.setRagioneSociale(cliente.getRagioneSociale());
			clienteUpdate.setPartitaIva(cliente.getPartitaIva());
			clienteUpdate.setEmail(cliente.getEmail());
			clienteUpdate.setDataUltimoContatto(LocalDate.now());
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
