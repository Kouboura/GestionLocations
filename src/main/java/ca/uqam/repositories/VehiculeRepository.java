package ca.uqam.repositories;

import ca.uqam.model.Vehicule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Mo-is-Balla on 2016-11-22.
 */
public interface VehiculeRepository extends CrudRepository<Vehicule,Long> {

    List<Vehicule> findAll();

	Iterable<Vehicule> findByState(String state);

	Vehicule findById(long id);
	
	Vehicule findByMatricule (String matricule);
;
	//Vehicule findOne(long id);

	//Vehicule findOne(String state);
}
