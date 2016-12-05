package ca.uqam.unittests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ca.uqam.config.Config;
import ca.uqam.console.App;
import ca.uqam.model.Client;
import ca.uqam.model.Location;
import ca.uqam.model.Vehicule;
//pour ce test, j'ai une voiture disponible et un client inscrit dans la base de donnees
//je veux verifier que l'application permet au client de louer et de retourner la voiture
import ca.uqam.repositories.ClientRepository;
import ca.uqam.repositories.LocationRepository;
import ca.uqam.repositories.VehiculeRepository;

public class LouerVoitureClientEnregistreTest {
	
	static ConfigurableApplicationContext context = SpringApplication.run(Config.class);
    static ClientRepository repositoryClient = context.getBean(ClientRepository.class);
    static VehiculeRepository repositoryVehicule= context.getBean(VehiculeRepository.class);
    static LocationRepository repositoryLocation = context.getBean(LocationRepository.class);
	
	Vehicule vehiculeTest = null;
	Client clientTest = null;
	Location locationTest = null;
	Vehicule voiture3 = null;
	
	Vehicule resultatTest = null;
	Vehicule vehiculeResultatTest = null;
	
	@Before
	public void setUp() throws Exception {

		clientTest = repositoryClient.findByPermitNumber("AM002310");
		vehiculeTest = repositoryVehicule.findByMatricule("20162009");
		
	}

	
	
	@Test
	public void voituresDisponiblesTest() {
    	ArrayList<Vehicule> listeVoituresDisponiblesTest = new ArrayList<Vehicule>();
    	listeVoituresDisponiblesTest = App.voituresDisponibles();
    	for (Vehicule voiture : listeVoituresDisponiblesTest){
    		if (voiture.getMatricule() == vehiculeTest.getMatricule())
    			voiture3 = voiture;
    	}
    	vehiculeTest.equals(voiture3);
	}
	

	@Test
	public void retrouverVehiculeTest() {
		Vehicule voiture1 = App.retrouverVehicule(vehiculeTest.getMatricule());
		voiture1.equals(vehiculeTest);
	}
	
	@Test
	public void verifierClientTest() {
		Client client1 = App.verifierClient(clientTest.getPermitNumber());
		client1.equals(clientTest);	
	}
	
	@Test
	public void loueVoitureTest() {
		App.louerVoiture(clientTest.getPermitNumber(), vehiculeTest.getMatricule());
		Iterable<Location> locations = repositoryLocation.findAll();
		vehiculeResultatTest = repositoryVehicule.findByMatricule(vehiculeTest.getMatricule());
		for (Location loc : locations) {
			if ( (loc.getClient().getId().equals(clientTest.getId())) && (loc.getVehicule().getId().equals(vehiculeTest.getId())) && (vehiculeResultatTest.getState().equals("Louee")) )
				locationTest = loc;
		}
		assertThat(locationTest.equals(null), equalTo(false));
		assertEquals(vehiculeResultatTest.getState().equals("Louee"), true);
	}
}
