@POEI2-717
Feature: Exécution des Tests de Plan de Test POEI2-710

	@POEI2-637
	Scenario Outline: Connexion avec plusieurs utilisateurs
		When il saisit le login "<username>" et le mot de passe "<password>"
		    Then le résultat de la connexion est "<resultat>"
		
		    Examples:
		      | username      | password     | resultat |
		      | locked_out_user | secret_sauce | success |
		
	@POEI2-663
	Scenario: Connexion tests login ok
		Given l'utilisateur est sur la page de connexion
		    When il saisit le login "standard_user" et le mot de passe "secret_sauce"
		    Then il est redirigé vers la page d'accueil
		
