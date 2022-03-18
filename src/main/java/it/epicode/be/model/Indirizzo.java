package it.epicode.be.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class Indirizzo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String via;
	private Integer civico;
	private String localita;
	private Integer cap;
	@Enumerated(EnumType.STRING)
	private TipoIndirizzo tipo;

	@ManyToOne
	@JsonIgnore
	private Comune comune;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnore
	private Cliente clienteIndirizzo;

	public Indirizzo addComune(Comune comune) {
		this.setComune(comune);
		return this;

	}

	public Indirizzo deleteComune(Long id) {
		if (this.comune.getId().equals(id)) {
			this.comune.getIndirizziComune().remove(this);
			return this;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Indirizzo [via=" + via + ", civico=" + civico + ", tipo=" + tipo + ", comune=" + comune.getNome()
				+ ", provincia=" + comune.getProvincia().getSigla() + ", cliente="
				+ clienteIndirizzo.getCognomeContatto() + " " + clienteIndirizzo.getNomeContatto() + "]";
	}

}
