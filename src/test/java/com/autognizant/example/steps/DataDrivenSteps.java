package com.autognizant.example.steps;

import java.util.Map;

import com.autognizant.core.cucumber.TestContext;
import com.autognizant.example.pages.HomePage;
import com.autognizant.example.pages.SearchResultPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DataDrivenSteps {

	private TestContext testContext;
	private HomePage homePage;
	private SearchResultPage searchResultPage;
	
	public DataDrivenSteps(TestContext context) throws Exception {
		testContext = context;
		homePage = new HomePage(testContext.getDriverManager().getAutognizantDriver());
		searchResultPage = new SearchResultPage(testContext.getDriverManager().getAutognizantDriver());		
	}
	
	@When("I search with keyword type {string}")
	public void i_search_with_keyword_type(String keywordType) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> data = ((Map<String, Map<String, String>>) testContext.getScenarioContext().getContext("TestData")).get(keywordType);
		
		homePage.searchKeyword(data.get("Keyword"));
	}
	
	@Then("I should get search results for {string}")
	public void i_should_get_search_results_for(String keywordType) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> data = ((Map<String, Map<String, String>>) testContext.getScenarioContext().getContext("TestData")).get(keywordType);
		
		String expectedResult = data.get("SearchResult");
		searchResultPage.verifySearchResult(expectedResult );
	}
}
