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
import ca.uqam.model.Vehicule;
import ca.uqam.repositories.ClientRepository;
import ca.uqam.repositories.LocationRepository;
import ca.uqam.repositories.VehiculeRepository;


public class AutresCasDeRetoursSteps {
	
	ConfigurableApplicationContext context;
    ClientRepository repositoryClient;
    VehiculeRepository repositoryVehicule;
    LocationRepository repositoryLocation;
    
    //Variables scenario 1
    Client client1 = null;
    Boolean valeur1 = true;
    
    //Variables scenario 2
    Vehicule voitureARetourner = null;
    Client client2 = null;
    Boolean valeur2 = true;
    Vehicule voiture3 = null;
    
    //Variables scenario 3
    Client client3 = null;
    Boolean valeur3 = true;
    
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
	@Given("un client de numero de permis $permitNumber qui n a pas loue de voiture")
	public void givenUnClientQuiNaPasLoueDeVoiture(String permitNumber){
		client1 = repositoryClient.findByPermitNumber(permitNumber);
		assertThat (client1.equals(null), equalTo(false));
	}
	
	
	@When("le client choisi l option retourner une voiture et entre son numero de permis")
	public void whenLeClientLoueLaVoiture(){
		valeur1 = App.verifierLocataire(client1.getPermitNumber());
	}
	
	@Then ("la methode verifierLocataire doit retourner $value")
	public void thenLaMethodeVerifierLocataireRetourneFaux(Boolean value) {
		assertThat(valeur1, equalTo(value));
	}
	
	//scenario 2
	@Given ("un client de numero de permis $permis qui veut retourner une voiture")
	public void givenUnClientQuiVeutRetournerVoitureDisponible(String permis){
		client2 = repositoryClient.findByPermitNumber(permis);
		assertThat (client2.equals(null), equalTo(false));
	}
	
	@Given ("une voiture de matricule $matricule $etat")
	public void givenUnClientQuiVeutRetournerVoitureDisponible(String matricule, String etat){
		voitureARetourner = repositoryVehicule.findByMatricule(matricule);
		assertThat(voitureARetourner.getState(), equalTo(etat));
	}
	
	@When ("le client veut retourner la dite voiture")
	public void whenLeClientVeutRetourneLaVoiture(){
		valeur2 = App.retournerVoiture(client2.getPermitNumber(), voitureARetourner.getMatricule());
	}
	
	@Then ("la methode verifierVoiture doit retourner $valeur")
	public void thenLaMethodeVerifierVoitureRetourneFaux(Boolean valeur){
		assertThat(App.verifierVoiture(voitureARetourner.getMatricule()), equalTo(valeur));
	}
	
	@Then ("la methode retournerVoiture doit retourner faux")
	public void thenLaMethodeRetourneVoitureRetourneFaux(){
		assertThat(valeur2, equalTo(false));
	}
	
	//scenario 3
	@Given("un client avec pour numero de permis $permitNumber")
	public void givenUnClientQuelconque(String permitNumber){
		client3 = repositoryClient.findByPermitNumber(permitNumber);
		assertThat (client3.equals(null), equalTo(false));
	}
	
	@Given("une voiture $etat qui a pour matricule $matricule mais pas louee par le dit client")
	public void givenuneVoiturePAsLoueeParLeClient(String matricule, String etat){
		voiture3 = repositoryVehicule.findByMatricule(matricule);
		assertThat (voiture3.equals(null), equalTo(false));
		assertThat(voiture3.getState(), equalTo(etat));
	}
	
	
	@When("le client choisi de retourner la dite voiture")
	public void whenLeClientChoisiDeRetournerLaDiteVoiture(){
		valeur3 = App.retournerVoiture(client3.getPermitNumber(), voiture3.getMatricule());
	}
	
	
	@Then ("le retour n est pas effectue")
	public void thenLeRetourNestPasEffectuee() {
		assertThat(valeur3, equalTo(false));
	}
	
}