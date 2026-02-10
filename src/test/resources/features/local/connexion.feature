Feature: Authentification

  Background:
    Given l'utilisateur est sur la page de connexion

  Scenario: Connexion avec des identifiants valides à l'application de sauceDemo
    When il saisit le login "standard_user" et le mot de passe "secret_sauce"
    Then il est redirigé vers la page d'accueil

  Scenario Outline: Connexion avec plusieurs utilisateurs
    When il saisit le login "<username>" et le mot de passe "<password>"
    Then le résultat de la connexion est "<resultat>"

    Examples:
      | username      | password     | resultat |
      | standard_user | secret_sauce | error  |
      | performance_glitch_user | secret_sauce | success  |

