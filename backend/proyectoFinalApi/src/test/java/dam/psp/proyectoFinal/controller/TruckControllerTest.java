package dam.psp.proyectoFinal.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import dam.psp.proyectoFinal.repository.BrandRepository;
import dam.psp.proyectoFinal.repository.ModelRepository;
import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.repository.TruckRepository;
import dam.psp.proyectoFinal.tablas.Brand;
import dam.psp.proyectoFinal.tablas.Model;
import dam.psp.proyectoFinal.tablas.Person;
import dam.psp.proyectoFinal.tablas.Truck;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TruckControllerTest {
	
	private static final int LIMITE = 3;

	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	private  TruckRepository truckRepository;
	
	@Autowired
	private  BrandRepository brandRepository;
	
	@Autowired
	private  ModelRepository modelRepository;
	
	@Autowired
	private  PersonRepository personRepository;
	
	@BeforeAll
	void creatTrucks() {
		for (int i = 0; i < LIMITE; i++) {
			Brand brand = brandRepository.save(new Brand(null, "marca" + i));
			Model model = modelRepository.save(new Model(null, "modelo" + i));
			Person person = personRepository.save(new Person(null, "usuario" + i, "lamrini" + i, "usuario" + i + "@gmail.com", "11111"));
			truckRepository.save(new Truck(null, brand, model, 150000, person));
		}
	}
	
	// TEST CREACIÓN DE CAMIONES.
	@Test
    @DirtiesContext
    void shouldCreateANewTruck() {
		Brand brand = brandRepository.save(new Brand(null, "renault"));
		Model model = modelRepository.save(new Model(null, "master"));
		Person person = personRepository.save(new Person(null, "isma", "lamrini", "isma@gmail.com", "1212"));
		Truck truckCreat = new Truck(null, brand, model, 150000, person);
		
		ResponseEntity<Truck> createResponse = restTemplate.postForEntity("/camiones", truckCreat , Truck.class); 
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		URI locationOfNewTruck = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewTruck, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		
		assertThat(truckCreat).isNotNull();

		assertThat(truckCreat.getBrand().getName()).isEqualTo("renault");
		assertThat(truckCreat.getModel().getName()).isEqualTo("master");
		assertThat(truckCreat.getPreci()).isEqualTo(150000.0);
		assertThat(truckCreat.getOwner().getName()).isEqualTo("isma");
		
	}
	
	@Test
	void shouldNotCreateANewTruck() {
		Brand brand = brandRepository.save(new Brand(null, "renault"));
		Model model = modelRepository.save(new Model(null, "master"));
		Person person = new Person(null, "isma", "lamrini", "isma@gmail.com", "1212"); // PERSONA NO GUARDADA EN LA BBDD.
		Truck truckCreat = new Truck(null, brand, model, 150000, person);
		
		ResponseEntity<Truck> createResponse = restTemplate.postForEntity("/camiones", truckCreat , Truck.class); 
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); // NO ENCUENTRA A ESE USUARIO EN LA BBDD.
	}
	
	// TEST ELEMINACIÓN DE CAMIONES.
	@Test
	@DirtiesContext
	void shouldDeleteATruck() {
		Brand brand = brandRepository.save(new Brand(null, "renault"));
		Model model = modelRepository.save(new Model(null, "master"));
		Person person = personRepository.save(new Person(null, "isma", "lamrini", "isma@gmail.com", "1212"));
		Truck truckCreat = truckRepository.save(new Truck(null, brand, model, 150000, person));
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

		ResponseEntity<Void> response = restTemplate.exchange("/camiones/{id}", HttpMethod.DELETE, requestEntity,
				Void.class, truckCreat.getId());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(truckRepository.findById(truckCreat.getId())).isEmpty();
	}
	
	@Test
	@DirtiesContext
	void shouldNotDeleteTruck() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

		ResponseEntity<Void> response = restTemplate.exchange("/camiones/1000", HttpMethod.DELETE, requestEntity,
				Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	// TEST RETORNO DE LISTA DE CAMIONES.
	@Test
	void shouldReturnAllTrucksWhenListIsRequested() {
	    ResponseEntity<Truck[]> response = restTemplate.getForEntity("/camiones", Truck[].class);
	    
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	    Truck[] trucks = response.getBody();
	    assertThat(trucks).isNotNull();
	}
	
	// TEST RETORNO DE CAMIONES POR ID.
	@Test
	void shouldReturnTruckOfTheOrderedId() {
		ResponseEntity<Truck> response = restTemplate.getForEntity("/camiones/1", Truck.class); 
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void shouldNotReturnATruckWithAnUnknownId() {
		ResponseEntity<Truck> response = restTemplate.getForEntity("/camiones/1000", Truck.class); 
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
