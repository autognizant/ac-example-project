package com.autognizant.example.steps;

import java.util.Map;

import com.autognizant.core.cucumber.AutomationHooks;
import com.autognizant.core.cucumber.TestContext;
import com.autognizant.core.util.Constants;
import com.autognizant.core.util.ExcelUtils;
import com.autognizant.core.util.Log;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ProjectAutomationHooks extends AutomationHooks{

	private TestContext testContext;
	private ExcelUtils excelUtils = new ExcelUtils();

	public ProjectAutomationHooks(TestContext context) {
		super(context);		
		testContext = context;
	}

	@Before
	public void beforeScenario(Scenario scenario) throws Exception {
		super.beforeScenario(scenario);
		//Fetch Test Data
		try {
			excelUtils.setExcelFile(Constants.RESOURCES_PATH+"/TestData/TestData.xlsx", scenarioUniqueID);
			Map<String, Map<String, String>> data = excelUtils.getExcelDataIntoHashMap();
			testContext.scenarioContext.setContext("TestData", data);
		}catch (Exception e) {
			Log.info("TestData is not available for this scenario !");
		}
		//connect to database connection
		//DatabaseConnection.getInstance().getConnection();
	}

	@After()
	public void afterScenario(Scenario scenario) throws Exception {
		super.afterScenario(scenario);
		//close database connection
		//DatabaseConnection.getInstance().closeConnection();
	}
}
