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


public class LouerRetournerClientNonEnregistreSteps {
	
	ConfigurableApplicationContext context;
    ClientRepository repositoryClient;
    VehiculeRepository repositoryVehicule;
    LocationRepository repositoryLocation;
    
    //Variables scenario 1
    //Variables d'entree
    Client clientNouveau = null;
    Vehicule voitureALouer = null;
    Location locationEffectuee = null;
    
    Client clientEnregistre = null;
    //Variables de sortie
    Client nouveauClient = null;
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
   
	@Given("un nouveau client de numero de permis $permitNumber")
	public void givenUnNouveauClient(String permitNumber){
		clientNouveau = repositoryClient.findByPermitNumber(permitNumber);
		assertThat (clientNouveau, equalTo(null));
	}
	
	@Given("une voiture dont l etat est $etat de matricule $matricule")
	public void givenUneVoitureDontLEtatDisponible(String matricule, String etat){
		voitureALouer = repositoryVehicule.findByMatricule(matricule);
		assertThat(voitureALouer.getState(), equalTo(etat));
	}
	
	@When("le nouveau client s identifie avec le numero de permis $numeroPermis le nom $nom le prenom $prenom le telephone $phone et l adresse $adresse")
	public void whenLeNouveauClientSIdentifie(String numeroPermis, String nom, String prenom, String phone, String adresse){
		nouveauClient = new Client(numeroPermis, nom, prenom, phone, adresse);
		App.save(nouveauClient);
	}
	
	@When("le nouveau client loue la voiture")
	public void whenLeNouveauClientLoueLaVoiture(){
		App.louerVoiture(nouveauClient.getPermitNumber(), voitureALouer.getMatricule());
	}
	
	
	@Then ("le nouveau client est enregistre dans la base de donnees")
	public void thenLeNouveauClientEstEnregistreDansLaBaseDeDonnees() {
		clientEnregistre = repositoryClient.findByPermitNumber(nouveauClient.getPermitNumber());
		assertThat (clientEnregistre.equals(null), equalTo(false));
	}
	
	@Then ("une location est creee")
	public void UneLocationEstCreee() {
		Iterable<Location> locations = repositoryLocation.findAll();
		voitureResultatLocation = repositoryVehicule.findByMatricule(voitureALouer.getMatricule());
		//clientResultatLocation = repositoryClient.findByPermitNumber(clientEnregistre.getPermitNumber());
		for (Location loc : locations) {
			if ( (loc.getClient().getId().equals(clientEnregistre.getId())) && (loc.getVehicule().getId().equals(voitureResultatLocation.getId())) && (voitureResultatLocation.getState().equals("Louee")) )
				locationEffectuee = loc;
		}
		assertThat(locationEffectuee.equals(null), equalTo(false));
	}
	
	@Then ("l etat de la voiture change et passe a $etat") 
	public void thenLEtatDeLaVoitureChangeEtPasseALouee(String etat) {
		voitureResultatLocation = repositoryVehicule.findByMatricule(voitureALouer.getMatricule());
		assertThat(voitureResultatLocation.getState(), equalTo(etat));
	}
	
	//scenario 2
	@Given ("un client de numero de permis $permis qui a louee une voiture de matricule $matricule")
	public void givenUneVoitureAEteLouee(String permis, String matricule){
		clientQuiRetourne = repositoryClient.findByPermitNumber(permis);
		voitureARetourner = repositoryVehicule.findByMatricule(matricule);
		assertThat(App.verifierLocataire(permis), equalTo(true));
		assertThat(App.verifierVoiture(matricule), equalTo(true));
	}
	
	@When ("le dit client retourne la voiture")
	public void whenLeDitClientRetourneLaVoiture(){
		assertThat(App.retournerVoiture(clientQuiRetourne.getPermitNumber(), voitureARetourner.getMatricule()), equalTo(true));
	}
	
	@Then ("l etat de la voiture louee par ce client devient $state")
	public void thenLetatDeLaVoitureLoueeParCeClientDevientDisponible(String state){
		voitureResultatRetour = repositoryVehicule.findByMatricule(voitureARetourner.getMatricule());
		assertThat(voitureResultatRetour.getState(), equalTo(state));
	}
}
