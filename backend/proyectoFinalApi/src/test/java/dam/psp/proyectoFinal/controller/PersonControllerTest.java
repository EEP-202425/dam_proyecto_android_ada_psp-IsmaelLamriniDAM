package dam.psp.proyectoFinal.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.tablas.Person;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PersonControllerTest {
	
	private static final int LIMITE = 3;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	private PersonRepository personRepository;

	@BeforeEach
	void createPerson() {
		for(int i = 0; i < LIMITE; i++) {
			personRepository.save(new Person(null, "usuario" + i, "apellido" + i, "usuario" + i + "@gmail.com", "1234"));
		}
	}

	@Test
	@DirtiesContext
	void shouldUpdateAnExistingPerson() {
		Person personUpdate = new Person(1, "isma", "lema", "isma@gmail.com", "4321");
		HttpEntity<Person> request = new HttpEntity<>(personUpdate);
		ResponseEntity<Void> response = restTemplate.exchange("/personas/1", HttpMethod.PUT, request , Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	@Test
	@DirtiesContext
	void shouldNotUpdateAnExistingPerson() {
		Person UnknowPerson = new Person(null, "ismaaa", "lemaaa", "ismaaa@gmail.com", "43210");
		HttpEntity<Person> request = new HttpEntity<>(UnknowPerson);
		ResponseEntity<Void> response = restTemplate.exchange("/personas/1000", HttpMethod.PUT, request , Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
