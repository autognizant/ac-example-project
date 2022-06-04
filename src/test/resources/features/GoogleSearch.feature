@search
Feature: Google search feature
  As a user, I should able to search any keyword and get results.

  @ACAUTO-Scenario1 @smoke
  Scenario: Search keyword test
    Given I am on home page
    When I search with keyword "Autognizant"
    Then I should get search results as "Autognizant"

  @ACAUTO-Scenario2 @regression
  Scenario Outline: Search keyword parameterized test
    Given I am on home page
    When I search with keyword "<Keyword>"
    Then I should get search results as "<Result>"

    Examples: 
      | Keyword     | Result           |
      | Autognizant | Autognizant      |
      | Framework   | Framework Laptop |
