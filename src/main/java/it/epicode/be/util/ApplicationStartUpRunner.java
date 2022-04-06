package it.epicode.be.util;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.epicode.be.model.Cliente;
import it.epicode.be.model.Fattura;
import it.epicode.be.model.StatoFattura;
import it.epicode.be.model.TipoCliente;
import it.epicode.be.repository.ClienteRepository;
import it.epicode.be.repository.FatturaRepository;

@Component
public class ApplicationStartUpRunner implements CommandLineRunner {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	FatturaRepository fatturaRepository;

	@Override
	public void run(String... args) throws Exception {
//		Cliente cliente = initCliente();
//		initFatture(cliente);

	}

	private Cliente initCliente() {
		Cliente cliente = Cliente.builder().ragioneSociale("CORPO").partitaIva("Prova").email("prova@prova.com")
				.dataInserimento(LocalDate.parse("2010-11-21")).dataUltimoContatto(LocalDate.now())
				.fatturatoAnnuale(20000.0).pec("prova@pec").telefono("393287262").emailContatto("contatto@prova.com")
				.nomeContatto("Emilio").cognomeContatto("Giorgioni").telefonoContatto("338382929").tipo(TipoCliente.SAS)
				.build();

		Cliente cliente2 = Cliente.builder().ragioneSociale("SAMURAI").partitaIva("Prova2").email("prova2@prova.com")
				.dataInserimento(LocalDate.parse("2009-01-11")).dataUltimoContatto(LocalDate.now())
				.fatturatoAnnuale(10000.0).pec("prova2@pec").telefono("393285432").emailContatto("contatto2@prova.com")
				.nomeContatto("Johnny").cognomeContatto("Silverhand").telefonoContatto("338436789")
				.tipo(TipoCliente.SRL).build();
		clienteRepository.save(cliente2);

		Cliente cliente3 = Cliente.builder().ragioneSociale("NOMAD").partitaIva("Prova3").email("prova3@prova.com")
				.dataInserimento(LocalDate.parse("2018-02-24")).dataUltimoContatto(LocalDate.now())
				.fatturatoAnnuale(20000.0).pec("prova3@pec").telefono("393287256").emailContatto("contatto3@prova.com")
				.nomeContatto("Panam").cognomeContatto("Palmer").telefonoContatto("338389563").tipo(TipoCliente.SPA)
				.build();
		clienteRepository.save(cliente3);

		return clienteRepository.save(cliente);
	}

	private Fattura initFatture(Cliente cliente) {

		StatoFattura stato = StatoFattura.builder().nome("PAGATO").build();
		Fattura fattura = Fattura.builder().anno(2022).importo(200.0).numero(123456).stato(stato).cliente(cliente)
				.build();

		fatturaRepository.save(fattura);

		return fattura;

	}

}
