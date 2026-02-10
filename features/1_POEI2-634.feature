Feature: Connexion avec des identifiants valides à l'application de sauceDemo

	Background:
		#@POEI2-635
		Given l'utilisateur est sur la page de connexion

	@POEI2-634
	Scenario: Connexion avec des identifiants valides à l'application de sauceDemo
		When il saisit le login "standard_user" et le mot de passe "secret_sauce"
		    Then il est redirigé vers la page d'accueil
		
