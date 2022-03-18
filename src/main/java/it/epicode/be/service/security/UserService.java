package it.epicode.be.service.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.model.security.Utente;
import it.epicode.be.repository.security.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<Utente> findById(Integer id) {
		return userRepository.findById(id);
	}

}
