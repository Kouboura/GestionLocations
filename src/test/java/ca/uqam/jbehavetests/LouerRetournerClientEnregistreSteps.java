package ca.uqam.jbehavetests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ca.uqam.config.Config;
import ca.uqam.console.App;
import ca.uqam.model.Client;
import ca.uqam.model.Location;
import ca.uqam.model.Vehicule;
import ca.uqam.repositories.ClientRepository;
import ca.uqam.repositories.LocationRepository;
import ca.uqam.repositories.VehiculeRepository;


public class LouerRetournerClientEnregistreSteps {
	
	ConfigurableApplicationContext context;
    ClientRepository repositoryClient;
    VehiculeRepository repositoryVehicule;
    LocationRepository repositoryLocation;
    
    //Variables scenario 1
    //Variables d'entree
    Client paul = null;
    Vehicule voitureX = null;
    Location locationEffectuee = null;
    
    //Variables de sortie
    Client clientResultatLocation = null;
    Vehicule voitureResultatLocation = null;
    
    //Variables scenario 2
    //variables d'entree
    Vehicule voitureARetourner = null;
    Client clientQuiRetourne = null;
    
    //Variables de sortie
    Vehicule voitureResultatRetour = null;
    
   @BeforeScenario
   public void setUp() {
		context = SpringApplication.run(Config.class);
	    repositoryClient = context.getBean(ClientRepository.class);
	    repositoryVehicule= context.getBean(VehiculeRepository.class);
	    repositoryLocation = context.getBean(LocationRepository.class);
   }
   
   @AfterScenario()
   public void tearDown(){
	   context.close();
   }
	@Given("un client de numero de permis $permitNumber enregistre dans la base de donnees")
	public void givenUnClient(String permitNumber){
		paul = repositoryClient.findByPermitNumber(permitNumber);
		assertThat (paul.equals(null), equalTo(false));
	}
	
	@Given("une voiture $matricule dont l etat est $etat")
	public void givenUneVoitureDisponible(String matricule, String etat){
		voitureX = repositoryVehicule.findByMatricule(matricule);
		assertThat(voitureX.getState(), equalTo(etat));
	}
	
	@When("le client loue la voiture")
	public void whenLeClientLoueLaVoiture(){
		App.louerVoiture(paul.getPermitNumber(), voitureX.getMatricule());
	}
	
	
	@Then ("une location est cree dans la base de donnees")
	public void thenLocationCree() {
		Iterable<Location> locations = repositoryLocation.findAll();
		voitureResultatLocation = repositoryVehicule.findByMatricule(voitureX.getMatricule());
		for (Location loc : locations) {
			if ( (loc.getClient().getId().equals(paul.getId())) && (loc.getVehicule().getId().equals(voitureX.getId())) && (voitureResultatLocation.getState().equals("Louee")) )
				locationEffectuee = loc;
		}
		assertThat(locationEffectuee.equals(null), equalTo(false));
	}
	
	@Then ("l etat de la voiture passe a $etat") 
	public void thenLEtatDeLaVoitureDevientLouee(String etat) {
		voitureResultatLocation = repositoryVehicule.findByMatricule(voitureX.getMatricule());
		assertThat(voitureResultatLocation.getState(), equalTo(etat));
	}
	
	//scenario 2
	@Given ("une voiture $matricule a ete loue par un client de numero de permis $permis")
	public void givenUneVoitureAEteLouee(String matricule, String permis){
		clientQuiRetourne = repositoryClient.findByPermitNumber(permis);
		voitureARetourner = repositoryVehicule.findByMatricule(matricule);
		assertThat(App.verifierLocataire(permis), equalTo(true));
		assertThat(App.verifierVoiture(matricule), equalTo(true));
	}
	
	@When ("le client retourne la voiture")
	public void whenLeClientRetourneLaVoiture(){
		assertThat(App.retournerVoiture(clientQuiRetourne.getPermitNumber(), voitureARetourner.getMatricule()), equalTo(true));
	}
	
	@Then ("l etat de la voiture devient $state")
	public void thenLaVoitureEstDisponible(String state){
		voitureResultatRetour = repositoryVehicule.findByMatricule(voitureARetourner.getMatricule());
		assertThat(voitureResultatRetour.getState(), equalTo(state));
	}
	
}