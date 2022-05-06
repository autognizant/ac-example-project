package com.autognizant.example.steps;

import com.autognizant.core.cucumber.TestContext;
import com.autognizant.example.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class HomePageSteps {

	private TestContext testContext;
	private HomePage homePage;

	public HomePageSteps(TestContext context) throws Exception {
		testContext = context;
		homePage = new HomePage(testContext.getDriverManager().getAutognizantDriver());
	}

	@Given("I am on home page")
	public void i_am_on_home_page() {
		homePage.launchApp();
	}

	@When("I search with keyword {string}")
	public void i_search_with_keyword(String keyword) throws Exception {
		homePage.searchKeyword(keyword);
	}
}