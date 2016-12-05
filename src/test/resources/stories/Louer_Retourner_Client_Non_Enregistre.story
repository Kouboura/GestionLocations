Sample story

Narrative:
In order to verifier que je peux louer une voiture a un client non enregistre dans la base de donnees et faire retourner la voiture
As a utilisateur
I want to louer une voiture a un client enregistre et retourner la voiture
					 
Scenario: un client non enregistre dans la base de donnees loue une voiture
Given un nouveau client de numero de permis MAMA18529503
Given une voiture dont l etat est Disponible de matricule 789596
When le nouveau client s identifie avec le numero de permis MAMA18529503 le nom MAMA le prenom Kouboura le telephone 5145683227 et l adresse 110RueDeLaBarre
When le nouveau client loue la voiture
Then le nouveau client est enregistre dans la base de donnees
And une location est creee
And l etat de la voiture change et passe a Louee

Scenario: un client qui a loue une voiture retourne la voiture
Given un client de numero de permis MAMA18529503 qui a louee une voiture de matricule 789596
When le dit client retourne la voiture
Then l etat de la voiture louee par ce client devient Disponible