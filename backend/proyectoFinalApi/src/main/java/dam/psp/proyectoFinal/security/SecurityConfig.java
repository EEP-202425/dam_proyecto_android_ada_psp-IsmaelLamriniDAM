package dam.psp.proyectoFinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import dam.psp.proyectoFinal.repository.PersonRepository;

@Configuration
public class SecurityConfig {
	
	private final PersonRepository personRepository;
	
	public SecurityConfig(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		Person p = personRepository.findByName(null)
//	}
}
