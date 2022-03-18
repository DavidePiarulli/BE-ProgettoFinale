package it.epicode.be.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.epicode.be.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Optional<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByFatturatoAnnuale(String prefix);

	public Optional<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByDataInserimento(String prefix);

	public Optional<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByDataUltimoContatto(String prefix);

	public Optional<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByIndirizziComuneProvinciaNome(String prefix);

	@Query("SELECT c FROM Cliente c WHERE c.fatturatoAnnuale > :fatturatoAnnuale")
	public Optional<Cliente> findByQueryParam(@Param("fatturatoAnnuale") Double fatturato);

	@Query("SELECT c FROM Cliente c WHERE c.dataInserimento = :dataInserimento")
	public Optional<Cliente> findByQueryParamInserimento(@Param("dataInserimento") LocalDate dataInserimento);

	@Query("SELECT c FROM Cliente c WHERE c.dataUltimoContatto = :dataUltimoContatto")
	public Optional<Cliente> findByQueryParamUltimoContatto(@Param("dataUltimoContatto") LocalDate dataUltimoContatto);

}
