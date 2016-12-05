package ca.uqam.model;

import org.dom4j.tree.AbstractEntity;

import ca.uqam.model.Client;
import ca.uqam.model.Vehicule;

import javax.persistence.*;

import java.util.Date;

/**
 * Created by Mo-is-Balla on 2016-11-26.
 */
@Table
@Entity(name = "Location")
public class Location extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idLocation;
    //location est lié à un client
    @ManyToOne(fetch = FetchType.EAGER , targetEntity=Client.class)
    @JoinColumn(name = "permisClient")
    private Client client;
    //location est lié à un vehicule
    @ManyToOne(fetch = FetchType.EAGER ,targetEntity=Vehicule.class)
    @JoinColumn(name = "MatriculeVehicule")
    private Vehicule vehicule;

    @Column(name = "permisClient", insertable = false, updatable = false)
    private String permisClient;
    @Column(name = "MatriculeVehicule", insertable = false, updatable = false)
    private String  matriculeVehicule;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_rent")
    private Date date_of_rent;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_return")
    private Date date_of_return;

	public Date getDateOfReturn(){
		return date_of_return;
	}
    
    public Location() {
    	
    }
    
    public Location( Client client, Vehicule vehicule) {
        this.date_of_rent = new Date();
        this.client = client;
        this.vehicule = vehicule;
        this.date_of_return = new Date(2010-10-10);

     }

    @Override
    public String toString() {
        return String.format("Location[permisClient='%s', matriculeVehicule= '%s', DateOfRent='%s', DateOfReturn='%s']",permisClient , matriculeVehicule, date_of_rent, date_of_return);
    }
    public Client getClient() {
        return this.client;
    }

    public Vehicule getVehicule() {
        return this.vehicule
                ;
    }
    
    public void setDateOfReturn(Date date) {
        this.date_of_return = date;
                ;
    }
    

    public boolean equals(Location obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
       /* if (this.idClient != other.idClient)  {
            return false;
        }
        if (this.idVehicule!= other.idVehicule) {
            return false;
        }*/
        if (this.date_of_rent != other.date_of_rent) {
            return false;
        }
        if (this.date_of_return!= other.date_of_return) {
            return false;
        }
        return true;
    }
   
   @Override
    public int hashCode() {
        int result = 17;
       // result = (int) (31 * result + idClient);
        //result = (int) (31 * result + idVehicule);
        result = 31 * result + date_of_rent.hashCode();
        result = 31 * result + date_of_return.hashCode();
        return result;
    }
}


