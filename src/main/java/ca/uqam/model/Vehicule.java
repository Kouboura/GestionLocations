package ca.uqam.model;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;

@Table
@Entity(name = "Vehicule")
public class Vehicule extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String matricule;
    @Column(name = "Marque")
    private String marque;
    @Column(name = "Model")
    private String model;
    @Column(name = "Type")
    private String type;
    @Column(name = "Year")
    private String year;
    @Column(name = "Price_per_day")
    private int price;
    @Column(name = "State")
    private String state;

    public Vehicule(){
    	
    }
    
    public Vehicule(String matricule, String marque, String model, String type, String year, int price, String state) {
    	this.matricule = matricule;
        this.marque = marque;
        this.model = model;
        this.type=type;
        this.year= year;
        this.price=price;
        this.state = state;

    }
    @Override
    public String toString() {
        return String.format("[Matricule=%s, Marque='%s', Model='%s', Type ='%s' , Year ='%s' ,Price = %s, Etat ='%s']", getMatricule(), marque, model, type, year, price, state);
    }
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public Long getId() {
		return id;
	}

	public void setState(String state) {
		this.state = state;
		
	}

	public String getState() {
		return this.state;
	}

	/*public void setId(long l) {
		this.id = l;
		
	}*/
	
	   
	    public boolean equals(Vehicule obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Vehicule other = (Vehicule) obj;
	       /* if (this.id != other.id)  {
	            return false;
	        }*/
	        if (this.marque!= other.marque) {
	            return false;
	        }
	        if (this.matricule != other.matricule) {
	            return false;
	        }
	        if (this.model!= other.model) {
	            return false;
	        }
	        if (this.price!= other.price) {
	            return false;
	        }
	        if (this.state!= other.state) {
	            return false;
	        }
	        if (this.type!= other.type) {
	            return false;
	        }
	        if (this.year!= other.year) {
	            return false;
	        }
	        return true;
	    }
	   
	   @Override
	    public int hashCode() {
	        int result = 17;
	       // result = (int) (31 * result + id);
	        result = 31 * result + marque.hashCode();
	        result = 31 * result + matricule.hashCode();
	        result = 31 * result + model.hashCode();
	        result = 31 * result + price;
	        result = 31 * result + state.hashCode();
	        result = 31 * result + type.hashCode();
	        result = 31 * result + year.hashCode();
	        return result;
	    }
}