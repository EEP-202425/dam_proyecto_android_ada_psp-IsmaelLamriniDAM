package dam.psp.proyectoFinal.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.tablas.Person;

@Service
public class PersonDetailsService implements UserDetailsService {
	
	private final PersonRepository personRepository;
	
	public PersonDetailsService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> optOwner = Optional.ofNullable(personRepository.findByName(username));
		
		if(!optOwner.isPresent()) {
			throw new UsernameNotFoundException("Usuario no encontrado: " + username);
		}
		
		Person owner = optOwner.get();
		
		return User.builder().username(owner.getName()).password(owner.getPassword()).roles("USER").build();
	}
	
	
	
}
