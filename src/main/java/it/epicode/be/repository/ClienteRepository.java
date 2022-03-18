package it.epicode.be.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.epicode.be.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Page<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByFatturatoAnnuale(String prefix,
			Pageable pageable);

	public Page<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByDataInserimento(String prefix,
			Pageable pageable);

	public Page<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByDataUltimoContatto(String prefix,
			Pageable pageable);

	public Page<Cliente> findByNomeContattoStartingWithIgnoreCaseOrderByIndirizziComuneProvinciaNome(String prefix,
			Pageable pageable);

	@Query("SELECT c FROM Cliente c WHERE c.fatturatoAnnuale > :fatturatoAnnuale")
	public Page<Cliente> findByQueryParam(@Param("fatturatoAnnuale") Double fatturato, Pageable pageable);

	@Query("SELECT c FROM Cliente c WHERE c.dataInserimento = :dataInserimento")
	public Page<Cliente> findByQueryParamInserimento(@Param("dataInserimento") LocalDate dataInserimento,
			Pageable pageable);

	@Query("SELECT c FROM Cliente c WHERE c.dataUltimoContatto = :dataUltimoContatto")
	public Page<Cliente> findByQueryParamUltimoContatto(@Param("dataUltimoContatto") LocalDate dataUltimoContatto,
			Pageable pageable);

}
