package dam.psp.proyectoFinal.controller;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dam.psp.proyectoFinal.repository.BrandRepository;
import dam.psp.proyectoFinal.repository.ModelRepository;
import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.repository.TruckRepository;
import dam.psp.proyectoFinal.tablas.Brand;
import dam.psp.proyectoFinal.tablas.Model;
import dam.psp.proyectoFinal.tablas.Person;
import dam.psp.proyectoFinal.tablas.Truck;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/camiones")
public class TruckController {
	
	private final TruckRepository truckRepository;
	private final PersonRepository personRepository;
	private final BrandRepository brandRepository;
	private final ModelRepository modelRepository;
	
	public TruckController(
			TruckRepository truckRepository, PersonRepository personRepository,
			BrandRepository brandRepository, ModelRepository modelRepository
			) {
			this.truckRepository = truckRepository;
			this.personRepository = personRepository;
			this.brandRepository = brandRepository;
			this.modelRepository = modelRepository;
	}
	
	@DeleteMapping("/{id}") 
	public ResponseEntity<Void> deleteTruck(@PathVariable int id) {
		
		Optional<Truck> optTruck = truckRepository.findById(id);
		if(!optTruck.isPresent()) {
			return ResponseEntity.notFound().build();
		} 
			
		truckRepository.delete(optTruck.get());
		return ResponseEntity.noContent().build();
	}
	
	// GUARDA EL NUEVO OBJETO CAMIÓN EN LA BBDD Y CREA SU URL.
	@PostMapping
	public ResponseEntity<Truck> createCamion(@RequestBody Truck newTruck, UriComponentsBuilder ucb, 	@AuthenticationPrincipal UserDetails userDetails) {
		
		Optional<Brand> optbrand = Optional.ofNullable(brandRepository.findByName(newTruck.getBrand().getName().toLowerCase()));
		Brand brand = optbrand.isPresent() ? optbrand.get() : brandRepository.save(new Brand(null, newTruck.getBrand().getName().toLowerCase()));
		
		Optional<Model> optModel = Optional.ofNullable(modelRepository.findByName(newTruck.getModel().getName().toLowerCase()));
		Model model = optModel.isPresent() ? optModel.get() : modelRepository.save(new Model(null, newTruck.getModel().getName().toLowerCase()));
		
		String userName = userDetails.getUsername();
		
		
		Optional<Person> optPerson = Optional.ofNullable(personRepository.findByName(userName));
		if(!optPerson.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Person owner = optPerson.get();
		
		Truck createTruck = new Truck(null ,brand, model, newTruck.getPreci(), owner);
		Truck truckSave = truckRepository.save(createTruck);
		URI newURL = ucb.path("camiones/{id}").buildAndExpand(truckSave.getId()).toUri();
		return ResponseEntity
	            .created(newURL)
	            .body(truckSave);
	}
	
	// DEVUELVE TODOS LOS CAMIONES REGISTRADOS.
	@GetMapping
	private ResponseEntity<Iterable<Truck>> getAll(Principal principal) {
		Iterable<Truck> trucks = truckRepository.findAll();
		return ResponseEntity.ok(trucks);
	}
	
	// DEVUELVO EL OBJETO COMPLETO DEL ID RECIBIDO.
	@GetMapping("/{id}") 
	private ResponseEntity<Truck> findById(@PathVariable int id, Principal principal) {
//		Optional<Person> owner = Optional.ofNullable(personRepository.findByName(principal.getName()));
//		Person ownerFind = null;
//		if(owner.isPresent()) {
//			ownerFind = owner.get();
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//		
		Optional<Truck> myTruck = truckRepository.findById(id);
		if (myTruck.isPresent()) {
			return ResponseEntity.ok(myTruck.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
}
