package ca.uqam.console;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.context.ConfigurableApplicationContext;

import ca.uqam.model.Client;
import ca.uqam.model.Vehicule;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ConfigurableApplicationContext context = App.context;
    static ArrayList<Vehicule> listeVoituresDisponibles = new ArrayList<Vehicule>();
	static ArrayList<PairVehiculeClient> lesVoituresLouees = new ArrayList<PairVehiculeClient>();
    
    public static void main(String[] args) {    	
    	//Pour reinitialiser le base de donnees, il faut enlever les commentaires de l instruction suivante
    	//App.initialiserBaseDeDonnees();    	
    	help();
    	
    }
     
		static void help() { 	
		    char choiceMenu ;
	    	System.out.println("\nMenu principal : \n");
	        System.out.println(
	                        "1 : Afficher les voitures disponibles\n " +
	                        "2 : Afficher les voitures louees \n " +
	                        "3 : Louer une voiture \n " +
	                        "4 : Retourner une voiture \n " +
	                        "5 : Quitter\n " + 
	                        "Que voulez-vous faire \n"
	                        );
	        String initialChoiceMenu = sc.nextLine();
	        choiceMenu = initialChoiceMenu.charAt(0);
	 
	    	switch (choiceMenu) {
	    	
	    	//******************Affichage des voitures disponibles***********************
	    		case '1':
	        		afficherVoituresDisponibles();
	        		retourMenuPrincipal();
	        		break;		
	        		
    		//******************Afficher les voitures louees****************************		
	        	case '2':
	        		lesVoituresLouees = App.locataireVoitures();
	        		if (lesVoituresLouees.size() == 0)
	                 	System.out.println("\n Aucune voiture louee!!");
	            	 else {
	        	        System.out.println("\n Liste des voitures louees :\n");
	        	        for (PairVehiculeClient pair : lesVoituresLouees)
	        	        	System.out.println(pair.getVehicule() + " est louee par " +pair.getClient());
	            	 }
	        		retourMenuPrincipal();
	        		break;	
	        		
    		//******************Location d'une voiture ****************************************
	        	case '3':
	        		Vehicule vehiculeChoisie = null;
	        		Client client2 = null;
	        		afficherVoituresDisponibles();
	        		String choiceUser;
	        		int i = 1;
	            	//Recuperer le choix de la voiture desiree par l'utilisateur
	            	do{
	            		if (i > 1) {
	            			System.out.println("Mauvaise entree ! Merci de reessayer\n");	
	            		}
	            		i++;
	            		System.out.println("Entrez le matricule de la voiture choisie ou appuyer sur \"h\" pour revenir");
	            		choiceUser = sc.nextLine();
	            		if (choiceUser.charAt(0) == 'h' || choiceUser.charAt(0) == 'H')
	            			help();
	            	} while (App.verifierMatriculeEntre(choiceUser) == false);
	            	vehiculeChoisie = App.retrouverVehicule(choiceUser);
	            	System.out.println("Vehicule choisi : " +vehiculeChoisie);
	         	    	System.out.println("Confirmer votre choix  : O ou N ?");
	         	    	String r = sc.nextLine();
	         	    	char r_c = r.charAt(0);
	         	    	if ((r_c == 'O') || (r_c == 'o')){
	            	//Entrer le numero de permis de conduire
	         	    		System.out.println("\nVeuillez saisir votre numero de permis de conduire :");
	         	    		String numeroPermis = sc.nextLine();
	         	    		client2 = App.verifierClient(numeroPermis);
	         	    		if (client2 == null){
	         	    				client2 = identifierClient(numeroPermis);
	         	    				App.save(client2);
	         	    				}
	         	    		App.louerVoiture(numeroPermis, vehiculeChoisie.getMatricule());
	         	    		System.out.println("\nVotre location a ete bien enregistree!!");
	         	    	}
	         	    	else 
	         	    		System.out.println("\nProbleme quelque part ! La procedure a ete annulee. Veuillez reesayer");
	            	 
	            	 retourMenuPrincipal();
	        		break;
	        			        		
    		//******************choisir de retourner une voiture**********************************
	        	case '4':
	        		System.out.println("Entrer le numero de votre permis : \n");
	            	String permisClientQuiRetourne = sc.nextLine();
	            	if (App.verifierLocataire(permisClientQuiRetourne).equals(false))
	            		System.out.println("Le client n a pas de voiture a retourner");
	            	else {
		            	System.out.println("Entrer le matricule de la voiture : \n");
		            	String matriculeVoitureARetourner = sc.nextLine();
		            	if (App.verifierVoiture(matriculeVoitureARetourner).equals(false)) 
		            		System.out.println("Un probleme quelque part, la voiture entre n a pas ete louee");
		            	else {
			            	Boolean b = App.retournerVoiture(permisClientQuiRetourne, matriculeVoitureARetourner);
			            	if (b.equals(true))
			            		System.out.println("\n Merci pour votre confiance !");
			            	else 
			            		System.out.println("Probleme quelque part! Le client n'a pas loue cette voiture");
			            }
	            	}
	            	retourMenuPrincipal();
	            	help();
	        		break;
    		//******************choisir de quitter l'application*********************************
	        	case '5':
	        		quitter();
	        		break;
	        		
	        	default:
	        		System.out.println("Mauvaise entree ! Veuillez reessayer");
	        		help();
	        		break;
	        }
	    }
	   
	    //retourner au menu principal de l'application
		private static void retourMenuPrincipal() {
			char reponse_c;
	    	do {
	    		System.out.println("\n******************************************");
				System.out.println("Souhaitez vous continuer? O / N");
	            String reponse = sc.nextLine();
	        	reponse_c = reponse.charAt(0);
	        	if ((reponse_c == 'O') || (reponse_c == 'o'))
	        		help();  
	        	else if ((reponse_c == 'N') || (reponse_c == 'n')) {
	        		System.out.println("Au plaisir de vous revoir!");
	        		context.close();
	        		System.exit(0);
	        	} 
	        	else 
	        		System.out.print("\nMauvaise entree");
	    	} while (reponse_c != 'O' || reponse_c != 'o' || reponse_c != 'N' || reponse_c != 'n');
			
		}
		
		//quitter l'application
		static void quitter () {
	    	char r_c;
	    	do {
		    	System.out.println("Etes vous sure de vouloir quitter ? O / N");
		    	String r = sc.nextLine();
		    	r_c = r.charAt(0);
		    	if ((r_c == 'O') || (r_c == 'o')){
		    		System.out.println("Merci d'avoir utilise notre application. Au plaisir de vous revoir!");
		    		context.close();
	        		sc.close();
		    		System.exit(0);
		    	}
		    	else if ((r_c == 'N') || (r_c == 'n'))
		    		help();
		    	else 
	        		System.out.print("\nMauvaise entree");
	    	} while(r_c != 'O' || r_c != 'o' || r_c != 'N' || r_c != 'n');
	    }
   
	    //Recuperation des informations du client
	    static Client identifierClient(String p) {
	    	Client nouveauClient = new Client();
	    	nouveauClient.setPermitNumber(p);
	    	System.out.println("Recuperation de vos donnees pour sauvegarde: ");
        	System.out.println("Prenom :");
        	nouveauClient.setFirstName(sc.nextLine());
        	
        	System.out.println("Nom :");
        	nouveauClient.setLastName(sc.nextLine());
        	int i = 1;
        	do {
        		i++;
        		if(i>1)
        			System.out.println("Le nombre de chiffres constitue par votre numero de telephone est incorrect. Veuillez reesayer ");
        		System.out.println("Numero de telephone :");
        		nouveauClient.setPhone(sc.nextLine());
        	} while (nouveauClient.getPhone().length() != 10);
        	
        	System.out.println("Votre addresse :");
        	nouveauClient.setAdresse(sc.nextLine());
        	
        	return nouveauClient;
	    }
	    
	    //Affichage des voitures disponibles
	    static void afficherVoituresDisponibles() {
	    	listeVoituresDisponibles = App.voituresDisponibles();
			if (listeVoituresDisponibles == null) {
	         	System.out.println("\n Aucune voiture n'est disponible!!\n");
	         }
	    	 else {
		        System.out.println("\n \n Liste des voitures disponibles :\n");
		        for (Vehicule voiture : listeVoituresDisponibles)
		        	System.out.println(voiture);
	    	 }
	    }
}	

