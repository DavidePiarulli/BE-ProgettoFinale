
package it.epicode.be.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Comune {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String progressivoComune;

	private String nome;

	@ManyToOne
	private Provincia provincia;

	private String codiceComune;

	@OneToMany(mappedBy = "comune")
	@JsonIgnore
	private List<Indirizzo> indirizziComune;

	public Comune(String progressivoComune, String nome, Provincia provincia, String codiceComune) {
		super();
		this.progressivoComune = progressivoComune;
		this.nome = nome;
		this.provincia = provincia;
		this.codiceComune = codiceComune;
	}

}
