package it.epicode.be.service.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.epicode.be.model.security.Utente;
import it.epicode.be.repository.security.RoleRepository;
import it.epicode.be.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserLoader implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
//		initUtente();
//		log.info("User created");
//		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
//
//		Role role = new Role();
//		role.setRoleName(Roles.ROLE_ADMIN);
//		Utente user = new Utente();
//		Set<Role> roles = new HashSet<>();
//		roles.add(role);
//		user.setUserName("admin");
//		user.setPassword(bCrypt.encode("admin"));
//		user.setEmail("admin@domain.com");
//		user.setRoles(roles);
//		user.setActive(true);
//		roleRepository.save(role);
//		userRepository.save(user);
//
//		role = new Role();
//		role.setRoleName(Roles.ROLE_USER);
//		user = new Utente();
//		roles = new HashSet<>();
//		roles.add(role);
//		user.setUserName("user");
//		user.setPassword(bCrypt.encode("user"));
//		user.setEmail("user@domain.com");
//		user.setRoles(roles);
//		user.setActive(true);
//		roleRepository.save(role);
//		userRepository.save(user);
	}

	private void initUtente() {
		Utente user = new Utente();
		user.setEmail("test@email.com");
		user.setUserName("test.test");
		user.setPassword("test");
		userRepository.save(user);
		log.info("Saved User: {}", user.getUserName());

	}

}
