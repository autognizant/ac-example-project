@search
Feature: Google search data driven feature
  As a user, I should able to search any keyword and get results.
  This test uses test data from excel sheet

  @ACAUTO-Scenario3 @regression
  Scenario Outline: Search keyword parameterized test with excel data
    Given I am on home page
    When I search with keyword type "<KeywordType>"
    Then I should get search results for "<KeywordType>"

    Examples: 
      | KeywordType |
      | General     |
      | Science     |
      | Technology  |
