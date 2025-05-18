package dam.psp.proyectoFinal.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dam.psp.proyectoFinal.ProyectoFinalPspApplication;
import dam.psp.proyectoFinal.repository.BrandRepository;
import dam.psp.proyectoFinal.repository.ModelRepository;
import dam.psp.proyectoFinal.repository.PersonRepository;
import dam.psp.proyectoFinal.repository.TruckRepository;
import dam.psp.proyectoFinal.tablas.Brand;
import dam.psp.proyectoFinal.tablas.Model;
import dam.psp.proyectoFinal.tablas.Person;
import dam.psp.proyectoFinal.tablas.Truck;

@RestController
@RequestMapping("/camiones")
public class TruckController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProyectoFinalPspApplication.class);
	
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
			logger.error("Delete: Camión no encontrado.");
			return ResponseEntity.notFound().build();
		} 
			
		truckRepository.delete(optTruck.get());
		logger.info("Camión con id: " + optTruck.get().getId() + " ha sido eliminado.");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	private ResponseEntity<Truck> createTruck(@RequestBody Truck newTruck, UriComponentsBuilder ucb) {
		
		Brand brand = brandRepository.save(new Brand(null, newTruck.getBrand().getName().toLowerCase()));
		
		Model model = modelRepository.save(new Model(null, newTruck.getModel().getName().toLowerCase()));
		
		Optional<Person> optPerson = Optional.ofNullable(personRepository.findByName(newTruck.getOwner().getName().toLowerCase()));
		if(!optPerson.isPresent()) {
			logger.error("CreateTruck: usuario no encontrado.");
			return ResponseEntity.notFound().build();
		}
		Person owner = optPerson.get();
		
		Truck createTruck = new Truck(null ,brand, model, newTruck.getPreci(), owner);
		Truck truckSave = truckRepository.save(createTruck);
		logger.info("Camión con id: " + truckSave.getId() + " ha sido guardado con éxito.");
		
		URI newURL = ucb.path("camiones/{id}").buildAndExpand(truckSave.getId()).toUri();
		return ResponseEntity.created(newURL).body(truckSave);
	}
	
	@GetMapping
	private ResponseEntity<List<Truck>> getAll(Pageable pageable) {
		Page<Truck> truck = truckRepository.findAll 
				(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSortOr(Sort.by(Sort.Direction.DESC, "preci"))));
		logger.info("Lista de camiones exitosa.");
		return ResponseEntity.ok(truck.getContent());
	}
	
	@GetMapping("/{id}") 
	private ResponseEntity<Truck> findById(@PathVariable int id) {
		Optional<Truck> myTruck = truckRepository.findById(id);
		if (myTruck.isPresent()) {
			logger.info("Camión con id: " + myTruck.get().getId() + " ha sido encontrado con éxito.");
			return ResponseEntity.ok(myTruck.get());
		} else {
			logger.error("FindById: Camión no encontrado.");
			return ResponseEntity.notFound().build();
		}
	}
	
}
