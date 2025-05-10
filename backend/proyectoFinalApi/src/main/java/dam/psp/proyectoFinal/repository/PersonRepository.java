package dam.psp.proyectoFinal.repository;

import org.springframework.data.repository.CrudRepository;

import dam.psp.proyectoFinal.tablas.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{
	
	Person findByName(String name);
	
}
