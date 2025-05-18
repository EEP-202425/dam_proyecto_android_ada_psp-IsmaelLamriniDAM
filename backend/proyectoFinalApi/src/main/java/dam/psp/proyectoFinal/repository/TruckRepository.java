package dam.psp.proyectoFinal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import dam.psp.proyectoFinal.tablas.Truck;

public interface TruckRepository extends CrudRepository<Truck, Integer>, PagingAndSortingRepository<Truck, Integer> {

}
