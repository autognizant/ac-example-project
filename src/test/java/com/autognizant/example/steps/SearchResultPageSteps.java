package com.autognizant.example.steps;

import java.util.Map;

import com.autognizant.core.cucumber.TestContext;
import com.autognizant.example.pages.SearchResultPage;

import io.cucumber.java.en.Then;

public class SearchResultPageSteps {

	private TestContext testContext;
	private SearchResultPage searchResultPage;
	public static Map<String,Map<String,String>> logindata;
	
	public SearchResultPageSteps(TestContext context) throws Exception {
		testContext = context;
		searchResultPage = new SearchResultPage(testContext.getDriverManager().getAutognizantDriver());
	}
	
	@Then("I should get search results as {string}")
	public void i_should_get_search_results_as(String expectedResult) throws Exception {
		searchResultPage.verifySearchResult(expectedResult);
	}
	

}
