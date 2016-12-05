package ca.uqam.console;

import ca.uqam.config.Config;

import ca.uqam.model.Client;
import ca.uqam.model.Location;
import ca.uqam.model.Vehicule;
import ca.uqam.repositories.ClientRepository;
import ca.uqam.repositories.LocationRepository;
import ca.uqam.repositories.VehiculeRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Date;

public class App {
	static ConfigurableApplicationContext context = SpringApplication.run(Config.class);
    static ClientRepository repositoryClient = context.getBean(ClientRepository.class);
    static VehiculeRepository repositoryVehicule= context.getBean(VehiculeRepository.class);
    static LocationRepository repositoryLocation = context.getBean(LocationRepository.class);

    
    //initialiser la base de donnees
    public static void initialiserBaseDeDonnees() {
	    repositoryVehicule.save(new Vehicule("789596","Toyota","Camry","Sedan","2016",120,"Disponible"));
	    repositoryVehicule.save(new Vehicule("20162009","Honda","Accord","SEDAN","2016",120, "Disponible"));
	    repositoryVehicule.save(new Vehicule("20162010","gip","Accord","SEDAN","2016",120, "Disponible"));
	    repositoryVehicule.save(new Vehicule("20162011","gip","Accord","SEDAN","2016",120, "Louee"));
	    
	    repositoryClient.save(new Client("AM002300","Armelle","Tenekeu","5147718969","1345 rue saint charles"));
	    repositoryClient.save(new Client("AM002310","Mama","Kouboura","5146740886","1345 chemin de Chambly"));
	    repositoryClient.save(new Client("AM002312","Moussa","Balla","5146212124","1345 rue de la barre"));
    }
    
    //determiner la liste de tous les clients enregistres dans la base de donnees
    public static ArrayList<Client> allCustomers() {
    	ArrayList<Client> list_clients = new ArrayList<Client>();
	    Iterable<Client> customers = repositoryClient.findAll();
	    for (Client customer : customers){
	        list_clients.add(customer);
	    }
	    return list_clients;
    }
    
    //determiner la liste de toutes les voitures enregistrees dans la base de donnees
    public static ArrayList<Vehicule> allCars() {
    	ArrayList<Vehicule> list_voitures = new ArrayList<Vehicule>();
	    Iterable<Vehicule> voitures = repositoryVehicule.findAll();
	    for (Vehicule voiture : voitures)
	        list_voitures.add(voiture);
	    return list_voitures;
    }
    
    //determiner la liste de toutes les locatons effectuees
    public static ArrayList<Location> allLocations() {
    	ArrayList<Location> list_locations = new ArrayList<Location>();
	    Iterable<Location> locations = repositoryLocation.findAll();
	    for (Location location : locations)
	        list_locations.add(location);
	    return list_locations;
    }
    
    
	//determiner la liste des voitures disponibles
   public static  ArrayList<Vehicule> voituresDisponibles(){
    	ArrayList<Vehicule> listeVoituresDisponibles = new ArrayList<Vehicule>();
    	Iterable<Vehicule> voitures = repositoryVehicule.findByState("Disponible");
	    for (Vehicule voiture : voitures){
	    	listeVoituresDisponibles.add(voiture);
	    }
    	return listeVoituresDisponibles;
    }
    
    
	//determiner la liste des voitures louees
    static ArrayList<Vehicule> voituresLouees(){
    	ArrayList<Vehicule> listeVoituresLouees = new ArrayList<Vehicule>();
    	Iterable<Vehicule> voitures = repositoryVehicule.findByState("Louee");
	    for (Vehicule voiture : voitures){
	    	listeVoituresLouees.add(voiture);
	    }
    	return listeVoituresLouees;
    }
    
    //retrouver un vehicule a partir de son matricule
    public static  Vehicule retrouverVehicule(String matricule){
    	 Vehicule voiture2 = repositoryVehicule.findByMatricule(matricule);
    	 return voiture2;
    }
    
    //verifier que le matricule de la voiture entre est correct et que la voiture est effectivement disponible
    public static Boolean verifierMatriculeEntre(String matricule){
    	Boolean b = false;
    	ArrayList<Vehicule> listeVoituresDisponibles = new ArrayList<Vehicule>();
    	listeVoituresDisponibles = voituresDisponibles();
    	for (Vehicule voiture : listeVoituresDisponibles) {
    		if (voiture.getMatricule().equals(matricule))
    			b = true;
    	}
    	return b;
    	
    }
    
    //verifier que le client appartient a la base de donnees
    public static  Client verifierClient(String numPermis){
    	Client client = null;
    	client = repositoryClient.findByPermitNumber(numPermis);
    	return client;
    }
    
    
     //louer une voiture
    public static  void louerVoiture(String permisClient, String matriculeVoiture){
	    Client client = repositoryClient.findByPermitNumber(permisClient);
	    Vehicule voiture = repositoryVehicule.findByMatricule(matriculeVoiture);
    	repositoryLocation.save(new Location(client, voiture));
	    voiture.setState("Louee");
        repositoryVehicule.save(voiture);
    }
    
    //verifier que le numero de permis entre a loue une voiture
    public static Boolean verifierLocataire(String permisClientQuiRetourne){
    	Client client = repositoryClient.findByPermitNumber(permisClientQuiRetourne);
    	Iterable<Location> listeLocations = repositoryLocation.findByClient(client);
    	ArrayList<Location> locationsClient = new ArrayList<Location>();
    	System.out.println(listeLocations);
    	if (listeLocations.toString().equals("[]"))
    		return false;
    	else {
    		for(Location loc: listeLocations) {
        		if (loc.getVehicule().getState().equals("Louee")){
        			locationsClient.add(loc);
        		}
        	}
    		if (locationsClient.size() == 0)
    			return false;
    		else 
    			return true;
    	}
    }
    
    //verifier que la voiture entre est a l etat loue
    public static Boolean verifierVoiture(String matriculeVoitureARetourner){
    	Iterable<Location> listeLocations = repositoryLocation.findByPermisClient(matriculeVoitureARetourner);
    	if (listeLocations.equals(null))
    		return false;
    	else {
    		if ( repositoryVehicule.findByMatricule(matriculeVoitureARetourner).getState().equals("Disponible") )
    			return false;
    		else 
    			return true;
    	}
    }
 
    //retourner la voiture
    public static Boolean retournerVoiture(String permisClient, String matriculeVoitureLouee) {    	
    	Vehicule voitureARendre = null;
    	Client clientConcerne = repositoryClient.findByPermitNumber(permisClient);
    	Iterable<Location> locationConcernes = repositoryLocation.findByClient(clientConcerne);
    	for(Location loc : locationConcernes) {
    		if ((loc.getVehicule().getMatricule().equals(matriculeVoitureLouee)) && (loc.getVehicule().getState().equals("Louee"))) {
    			voitureARendre = repositoryVehicule.findByMatricule(loc.getVehicule().getMatricule());
    			loc.setDateOfReturn(new Date());
    			repositoryLocation.save(loc);
    			voitureARendre.setState("Disponible");
    			repositoryVehicule.save(voitureARendre);
    			return true;
    		}
    	}
    	return false;
}

    
    //retouner une location effectuee et pas encore retournee
   public static Location locationEffectuee(String permisClient, String matriculeVehicule){
    	Iterable<Location> listeLoc = repositoryLocation.findByMatriculeVehicule(matriculeVehicule);
    	Location loc2 = null;
    	for (Location loc : listeLoc){
    		if ((loc.getClient().getPermitNumber().equals(permisClient)) || (loc.getDateOfReturn().toString().equals("1969-12-31"))) 
    			loc2 = loc;
    	}
    	return loc2;    	
    }
    
    
    //afficher les locations effectuees avec les clients concernes
	public static ArrayList<PairVehiculeClient> locataireVoitures(){
		ArrayList<PairVehiculeClient> resultat= new ArrayList<PairVehiculeClient>();
    	Iterable<Location> listeLocations = repositoryLocation.findAll();
    	for (Location loc : listeLocations){
    		if (loc.getVehicule().getState().equals("Louee")){
    			PairVehiculeClient liste2 = new PairVehiculeClient(loc.getVehicule(), loc.getClient());
	    		resultat.add(liste2);
    		}
		}
    	System.out.println(resultat.size());
    	return resultat;
    }

    //afficher les voitures a l etat louee avec les clients concernes
	public static ArrayList<PairVehiculeClient> locataireVoituresLouees(){
    	ArrayList<Vehicule> listeVoituresLouees = new ArrayList<Vehicule>();
		ArrayList<PairVehiculeClient> resultat= new ArrayList<PairVehiculeClient>();
    	Iterable<Location> loc = null;
    	listeVoituresLouees = voituresLouees();
    	for (Vehicule v : listeVoituresLouees){
    		loc = repositoryLocation.findByVehicule(v);
    	}
		for (Location l : loc) {
			PairVehiculeClient liste2 = new PairVehiculeClient(l.getVehicule(), l.getClient());
    		resultat.add(liste2);
		}
    	return resultat;
    }
	public static void save(Client client) {
		repositoryClient.save(client);	
	}
}