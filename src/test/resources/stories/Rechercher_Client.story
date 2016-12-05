Sample story

Narrative:
As a user
I want to display an existing customer
So that I can see  the customer display

Scenario: Je veux chercher un client existant
Given Le client existe dans la base : AM002300
When Je cherche le client
Then je devrais avoir les detailles de client: <Nom> <Prenom> <Numerotel> <Adresse>
Examples:
          | Nom     | Prenom  | Numerotel  | Adresse               |
          | Armelle | Tenekeu | 5147718969 | 1345 rue saint charles|
