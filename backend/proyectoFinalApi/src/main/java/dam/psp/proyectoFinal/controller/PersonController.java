package dam.psp.proyectoFinal.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.tablas.Person;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/personas")
public class PersonController {
	
	private final PersonRepository personRepository;

	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@PostMapping
	private ResponseEntity<Void> CreatePerson(@Valid @RequestBody Person person, UriComponentsBuilder ucb) {
		
		String newMail = firstLetterTolowerCase(person.getMail());
		
		Person owner = new Person(null, person.getName(), person.getLastName().toLowerCase(), newMail, person.getPassword());
		Person ownerSave = personRepository.save(owner);
		URI newURL = ucb.path("personas/{id}").buildAndExpand(ownerSave.getId()).toUri();
		return ResponseEntity.created(newURL).build();
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<Void> updatePerson(@PathVariable int id, @RequestBody Person personUpdate) {
		Optional<Person> optPerson = personRepository.findById(id);
		if(!optPerson.isPresent()) {
			return ResponseEntity.notFound().build();
		} 
		
		Person person = optPerson.get();
		
		person.setName(personUpdate.getName());
		person.setLastName(personUpdate.getLastName().toLowerCase());
		person.setMail(personUpdate.getMail().toLowerCase());
		person.setPassword(personUpdate.getPassword());
		
		personRepository.save(person);
		
		return ResponseEntity.noContent().build();
	}
	
	private static String firstLetterTolowerCase(String mail) {
		String firstLetter = mail.substring(0, 1).toLowerCase();
		String newMail = firstLetter + mail.substring(1);
		return newMail;
	}
	
}
