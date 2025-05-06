package dam.psp.proyectoFinal.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dam.psp.proyectoFinal.repository.BrandRepository;
import dam.psp.proyectoFinal.repository.ModelRepository;
import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.repository.TruckRepository;
import dam.psp.proyectoFinal.tablas.Brand;
import dam.psp.proyectoFinal.tablas.Model;
import dam.psp.proyectoFinal.tablas.Person;
import dam.psp.proyectoFinal.tablas.Truck;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TruckControllerTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	private  TruckRepository truckRepository;
	
	@Autowired
	private  BrandRepository brandRepository;
	
	@Autowired
	private  ModelRepository modelRepository;
	
	@Autowired
	private  PersonRepository PersonRepository;

	@Test
	void testGetsForId() {
		
		Brand brand = brandRepository.save(new Brand(null, "renault"));
		Model model = modelRepository.save(new Model(null, "master"));
		Person person = PersonRepository.save(new Person(null, "isma", "lamrini", "isma@gmail.com", "1212"));
		
		Truck truckCreat = truckRepository.save(new Truck(null, brand, model, 150000, person));
		
		ResponseEntity<Truck> response = restTemplate.getForEntity("/camiones/1", Truck.class); 
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
