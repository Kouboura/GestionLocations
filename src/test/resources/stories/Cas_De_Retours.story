Sample story

Narrative:
In order to tester d autres cas different du happy path
As a utilisateur
I want to verifier que l application retorune la voiture a celui qui l a effectivement louee
					 
Scenario: un client enregistre dans la base de donnees veut retourner une voiture alors qu'il n'a pas loue de voiture
Given un client de numero de permis AM002300 qui n a pas loue de voiture
When le client choisi l option retourner une voiture et entre son numero de permis
Then la methode verifierLocataire doit retourner faux

Scenario: un client enregistre dans la base de donnees retourne une voiture qui n a pas ete loue
Given un client de numero de permis AM002310 qui veut retourner une voiture
Given une voiture de matricule 20162010 Disponible
When le client veut retourner la dite voiture
Then la methode verifierVoiture doit retourner faux
And la methode retournerVoiture doit retourner faux

Scenario: un client retourne une voiture mais il n est pas celui qui a loue la voiture
Given un client avec pour numero de permis AM002310
Given une voiture Louee qui a pour matricule 20162011 mais pas louee par le dit client
When le client choisi de retourner la dite voiture
Then le retour n est pas effectue