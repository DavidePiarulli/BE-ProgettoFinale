package it.epicode.be.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import it.epicode.be.model.security.Utente;

public interface UserRepository extends JpaRepository<Utente, Integer> {

	public Optional<Utente> findById(Integer id);

	public Optional<Utente> findByUserName(String userName);

	public boolean existsByEmail(String email);

	public boolean existsByUserName(String userName);
}
