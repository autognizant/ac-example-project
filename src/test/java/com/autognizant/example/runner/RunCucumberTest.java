package com.autognizant.example.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"com.autognizant.example.steps"},
		tags = "@ACAUTO-Scenario1",
		plugin = {"pretty", "html:target/cucumber/cucumber.html","json:target/cucumber/cucumber.json"})
public class RunCucumberTest extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
