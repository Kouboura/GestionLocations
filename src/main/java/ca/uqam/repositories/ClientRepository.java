package ca.uqam.repositories;

import ca.uqam.model.Client;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by Mo-is-Balla on 2016-11-14.
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

	Client findByPermitNumber(String permitNumber);

	Client findById(Long id);
}