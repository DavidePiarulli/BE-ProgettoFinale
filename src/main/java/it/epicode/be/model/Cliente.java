package it.epicode.be.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ragioneSociale;
	private String partitaIva;
	private String email;
	@JsonIgnore
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	private Double fatturatoAnnuale;
	private String pec;
	private String telefono;
	private String emailContatto;
	private String nomeContatto;
	private String cognomeContatto;
	private String telefonoContatto;
	@Enumerated(EnumType.STRING)
	private TipoCliente tipo;

	@OneToMany(mappedBy = "clienteIndirizzo", cascade = CascadeType.REMOVE)
	private List<Indirizzo> indirizzi;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
	private List<Fattura> fatture;

	public Cliente addFattura(Fattura fattura) {
		this.fatture.add(fattura);
		fattura.setCliente(this);
		return this;
	}

	public Cliente addIndirizzo(Indirizzo indirizzo) {
		this.indirizzi.add(indirizzo);
		indirizzo.setClienteIndirizzo(this);
		return this;
	}

	public Cliente deleteFattura(Long fatturaId) {
		Fattura fattura = this.fatture.stream().filter(f -> Objects.equals(f.getId(), fatturaId)).findFirst()
				.orElse(null);
		if (fattura != null) {
			this.fatture.remove(fattura);
			return this;
		}
		return null;
	}

	public Cliente deleteIndirizzo(Long indirizzoId) {
		Indirizzo indirizzo = this.indirizzi.stream().filter(i -> Objects.equals(i.getId(), indirizzoId)).findFirst()
				.orElse(null);
		if (indirizzo != null) {
			this.indirizzi.remove(indirizzo);
			return this;
		}
		return null;
	}

}
