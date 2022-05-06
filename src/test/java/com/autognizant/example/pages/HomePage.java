package com.autognizant.example.pages;

import org.testng.Assert;

import com.autognizant.core.config.CoreConfig;
import com.autognizant.core.selenium.AutognizantDriver;

public class HomePage{

	private AutognizantDriver driver;

	public HomePage(AutognizantDriver driver) {
		this.driver = driver;
	}

	public void launchApp() {
		driver.openBrowser(CoreConfig.getApplicationURL());
	}

	public void verifyHomePageIsDisplayed() throws Exception {
		Assert.assertTrue(driver.isElementDisplayed("Google Image"), "Google_Image is not displayed");
		Assert.assertTrue(driver.isElementDisplayed("Google Search Button"), "Google Search Button is not displayed");
	}

	public void searchKeyword(String keyword) throws Exception {
		driver.enterText("Google Search TextBox", keyword);
		driver.submitForm("Google Search Button");
	}
}
