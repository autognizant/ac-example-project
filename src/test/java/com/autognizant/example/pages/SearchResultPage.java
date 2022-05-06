package com.autognizant.example.pages;

import org.testng.Assert;

import com.autognizant.core.selenium.AutognizantDriver;

public class SearchResultPage{

	private AutognizantDriver driver;

	public SearchResultPage(AutognizantDriver driver) {
		this.driver = driver;
	}
	
	public void verifySearchResult(String expectedResult) throws Exception {
		Assert.assertTrue(driver.getElementText("Search Result Text").contains(expectedResult), expectedResult + " text is not found");		
	}
}
