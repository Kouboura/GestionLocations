package ca.uqam.unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ca.uqam.config.Config;
import ca.uqam.console.App;
import ca.uqam.model.Client;
import ca.uqam.model.Vehicule;
import ca.uqam.repositories.ClientRepository;
import ca.uqam.repositories.LocationRepository;
import ca.uqam.repositories.VehiculeRepository;


//pour ce test, j'ai une voiture disponible et un client inscrit dans la base de donnees
//je veux verifier que l'application permet au client de louer et de retourner la voiture  

public class RetournerVoitureTest {
	
	static ConfigurableApplicationContext context = SpringApplication.run(Config.class);
    static ClientRepository repositoryClient = context.getBean(ClientRepository.class);
    static VehiculeRepository repositoryVehicule= context.getBean(VehiculeRepository.class);
    static LocationRepository repositoryLocation = context.getBean(LocationRepository.class);
	
	Vehicule vehiculeTest = null;
	Client clientTest = null;
	
	
	@Before
	public void setUp() throws Exception {
		clientTest = repositoryClient.findByPermitNumber("AM002310");
		vehiculeTest = repositoryVehicule.findByMatricule("20162009");
		
	}
	
	/*@Test
	public void verifierLocataireTest() {
		assertEquals(App.verifierLocataire(clientTest.getPermitNumber()), true);
	}
	
	@Test
	public void verifierVoitureTest() {
		assertEquals(App.verifierVoiture(vehiculeTest.getMatricule()), true);
	}*/
	
	@Test
	public void retournerVoitureTest() {
		assertEquals(App.verifierLocataire(clientTest.getPermitNumber()), true);
		assertEquals(App.verifierVoiture(vehiculeTest.getMatricule()), true);
		assertEquals(App.retournerVoiture(clientTest.getPermitNumber(), vehiculeTest.getMatricule()), true);
	}
}
