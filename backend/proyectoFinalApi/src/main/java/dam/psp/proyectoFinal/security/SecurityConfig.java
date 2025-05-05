package dam.psp.proyectoFinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import dam.psp.proyectoFinal.repository.PersonRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final PersonRepository personRepository;
	private final PersonDetailsService personDetailsService;

    
    
	public SecurityConfig(PersonRepository personRepository, PersonDetailsService personDetailsService) {
		this.personRepository = personRepository;
		this.personDetailsService = personDetailsService;
	}
	

	@Bean // ESTA AUTENTICACION ES PARA NUESTRA PETICION POST DE CAMIONES
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  http
	      .csrf(csrf -> csrf.disable())
	      .authorizeHttpRequests(auth -> auth
	          .requestMatchers(HttpMethod.POST, "/camiones/**").authenticated()
	          .anyRequest().permitAll()
	      )
	      .httpBasic(withDefaults());

	    return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return personDetailsService;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
