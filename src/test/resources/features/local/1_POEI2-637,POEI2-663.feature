@POEI2-717
Feature: Exécution des Tests de Plan de Test POEI2-710

	@POEI2-637
	Scenario Outline: Connexion avec plusieurs utilisateurs
		Given l'utilisateur est sur la page de connexion
		When il saisit le login "<login>" et le mot de passe "<password>"
		Then le statut de la connexion devrait être "<resultat>"

		Examples:
			| login                     | password      | resultat |
			| standard_user             | secret_sauce | success  |
			| problem_user              | secret_sauce | error    |
			| performance_glitch_user   | secret_sauce | success  |
			| locked_out_user           | secret_sauce | error    |


	@POEI2-663
	Scenario: Connexion tests login ok
		Given l'utilisateur est sur la page de connexion
		    When il saisit le login "standard_user" et le mot de passe "secret_sauce"
		    Then il est redirigé vers la page d'accueil

