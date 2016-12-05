package ca.uqam.console;

import ca.uqam.model.Client;
import ca.uqam.model.Vehicule;

public class PairVehiculeClient {

	  private  Client client;
	  private  Vehicule vehicule;

	  public PairVehiculeClient(Vehicule vehicule, Client client) {
	    this.client = client;
	    this.vehicule = vehicule;
	  }

	  public Client getClient() { 
		  return this.client; 
		  }
	  
	  public Vehicule getVehicule() {
		  return this.vehicule; 
		  }
	  
	  public void setClient(Client client) { 
		  this.client = client; 
		  }
	  
	  public void setVehicule(Vehicule vehicule) { 
		  this.vehicule = vehicule;
		  }
}
