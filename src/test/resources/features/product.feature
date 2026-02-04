Feature: Ajout d'un produit au panier

  Background:
    Given l'utilisateur est connecté à l'application SauceDemo

  Scenario: Ajouter un produit au panier
    When il ajoute le produit "Sauce Labs Backpack" au panier
    Then le panier contient 1 produit





