Feature: Connexion avec plusieurs utilisateurs

	@POEI2-637
	Scenario Outline: Connexion avec plusieurs utilisateurs
		When il saisit le login "<username>" et le mot de passe "<password>"
		    Then le résultat de la connexion est "<resultat>"
		
		    Examples:
		      | username      | password     | resultat |
		      | standard_user | secret_sauce | error  |
		      | problem_user | secret_sauce | error  |
		      | performance_glitch_user | secret_sauce | success  |
		      | locked_out_user | secret_sauce | success |
		
