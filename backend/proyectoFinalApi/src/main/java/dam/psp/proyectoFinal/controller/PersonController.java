package dam.psp.proyectoFinal.controller;

import java.net.URI;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.tablas.Person;

@RestController
@RequestMapping("/personas")
public class PersonController {
	
	private final PersonRepository personRepository;

	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@PostMapping
	private ResponseEntity<Void> CreatePerson(@RequestBody Person person, UriComponentsBuilder ucb, Principal principal) {
		Person owner = new Person(null, person.getName(), person.getLastName(), person.getMail(), person.getPassword());
		Person ownerSave = personRepository.save(owner);
		URI newURL = ucb.path("personas/{id}").buildAndExpand(ownerSave.getId()).toUri();
		return ResponseEntity.created(newURL).build();
	}
	
	
}
