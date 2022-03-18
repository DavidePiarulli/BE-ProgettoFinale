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
		Cliente cliente = Cliente.builder().ragioneSociale("BOH").partitaIva("Prova").email("prova@prova.com")
				.dataInserimento(LocalDate.parse("2010-11-21")).dataUltimoContatto(LocalDate.now())
				.fatturatoAnnuale(1000.0).pec("prova@pec").telefono("393287262").emailContatto("conatto@emila.com")
				.nomeContatto("Provono").cognomeContatto("Giorgioni").telefonoContatto("338382929")
				.tipo(TipoCliente.SAS).build();
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
