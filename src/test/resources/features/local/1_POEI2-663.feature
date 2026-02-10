Feature: Connexion tests login ok

	@POEI2-663
	Scenario: Connexion tests login ok
		Given l'utilisateur est sur la page de connexion
		    When il saisit le login "standard_user" et le mot de passe "secret_sauce"
		    Then il est redirigé vers la page d'accueil
		
