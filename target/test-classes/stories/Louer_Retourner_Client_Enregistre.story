Sample story

Narrative:
In order to verifier que je peux louer une voiture a un client enregistre dans la base de donnees et faire retourner la voiture
As a utilisateur
I want to louer une voiture a un client enregistre et retourner la voiture
					 
Scenario: un client enregistre dans la base de donnees loue une voiture
Given un client de numero de permis AM002300 enregistre dans la base de donnees
Given une voiture 789596 dont l etat est Disponible
When le client loue la voiture
Then une location est cree dans la base de donnees
And l etat de la voiture passe a Louee

Scenario: le client ci dessus retourne la voiture
Given une voiture 789596 a ete loue par un client de numero de permis AM002300
When le client retourne la voiture
Then l etat de la voiture devient Disponible