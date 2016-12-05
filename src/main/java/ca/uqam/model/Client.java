package ca.uqam.model;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
/**
 * Created by Mo-is-Balla on 2016-11-14.
 */
@Table
@Entity(name = "Client")
public class Client extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String permitNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "Adresse")
    private String adresse;
    
    public Client(){

        // Default constructor
    }
    public Client(String permit, String firstName, String lastName, String phone, String adresse) {
    	this.setPermitNumber(permit);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone=phone;
        this.adresse=adresse;

    }

    @Override
    public String toString() {
        return String.format("Client[id=%d, Permis=%s, firstName='%s', lastName='%s', phone ='%s' , adresse ='%s']", id, getPermitNumber(), firstName, lastName, phone, adresse);
    }
	
    
    //getters et setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public void setPermitNumber(String permitNumber) {
		this.permitNumber = permitNumber;
	}
	public Long getId() {
		return id;
	}
	public String getPermitNumber() {
		return this.permitNumber;
	}
	public String getPhone() {
		// TODO Auto-generated method stub
		return this.phone;
	}
	public String getPermis() {
		return this.permitNumber;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public String getAdresse() {
		return this.adresse;
	}
	public void setId(Long i) {
		this.id = i;
		
	}
	
	  
	    public boolean equals(Client obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Client other = (Client) obj;
	        /*if (this.id != other.id)  {
	            return false;
	        }*/
	        if (this.firstName!= other.firstName) {
	            return false;
	        }
	        if (this.lastName != other.lastName) {
	            return false;
	        }
	        if (this.adresse!= other.adresse) {
	            return false;
	        }
	        if (this.phone!= other.phone) {
	            return false;
	        }
	        if (this.permitNumber!= other.permitNumber) {
	            return false;
	        }
	        return true;
	    }
	   
	   @Override
	    public int hashCode() {
	        int result = 17;
	        //result = (int) (31 * result + id);
	        result = 31 * result + lastName.hashCode();
	        result = 31 * result + firstName.hashCode();
	        result = 31 * result + adresse.hashCode();
	        result = 31 * result + phone.hashCode();
	        result = 31 * result + permitNumber.hashCode();
	        return result;
	    }
}
