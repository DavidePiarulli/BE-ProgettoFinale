package it.epicode.be.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.epicode.be.model.Fattura;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {

	@Query("SELECT f FROM Fattura f WHERE f.cliente.id = :clienteId")
	public Optional<Fattura> findByQueryParam(@Param("clienteId") Long clienteId);

	@Query("SELECT f FROM Fattura f WHERE f.stato = :stato")
	public Optional<Fattura> findByQueryParamStato(@Param("stato") String stato);

	@Query("SELECT f FROM Fattura f WHERE f.date = :date")
	public Optional<Fattura> findByQueryParamDate(@Param("date") Date date);

	@Query("SELECT f FROM Fattura f WHERE f.anno = :anno")
	public Optional<Fattura> findByQueryParamAnno(@Param("anno") Integer anno);

	@Query("SELECT f FROM Fattura f WHERE f.importo > :importo")
	public Optional<Fattura> findByQueryParamImporto(@Param("importo") BigDecimal importo);

}
