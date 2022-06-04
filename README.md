# ![image](https://user-images.githubusercontent.com/12494447/167223520-287fce73-bcfe-4069-b553-7b672140a23c.png)

# Simplify web automation testing !

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [Autognizant Example Project](#autognizant-example-project)
- [Pre-requisites](#pre-requisites)
  - [Setting up Example Project](#setting-up-example-project)
- [Documentation](#documentation)
    - [Writing a test](#writing-a-test)
      - [Create a feature file and scenario](#create-a-feature-file-and-scenario)
      - [Create Project Automation Hooks class](#create-project-automation-hooks-class)
      - [Create cucumber step definition classes](#create-cucumber-step-definition-classes)
      - [Create Page Object Model classes](#create-page-object-model-classes)
      - [Create object repository](#create-object-repository)
    - [Prepare test data](#prepare-test-data)
    - [Set test execution parameters](#set-test-execution-parameters)
    - [Set up runner class](#set-up-runner-class)
    - [Running tests](#running-tests)
    - [Execution results](#execution-results)
  - [Appendix](#appendix)
    - [Building](#building)
    - [License](#license)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Autognizant Example Project

Autognizant Example Project demonstrates how to write automation test script to test Web application using AC Framework. It enables you to write and execute automated acceptance/functional tests. It is cross-platform, open source and free. Automate your test cases with minimal coding.

# Pre-requisites
- <a href="https://java.com/en/download/manual.jsp" target="_blank">Java >16</a>
- <a href="https://maven.apache.org/download.cgi" target="_blank">Maven > 3.8.1</a>
- <a href="https:https://eclipse.org/downloads/" target="_blank">Eclipse</a>
- Eclipse Plugins
  	- <a href="http://download.eclipse.org/technology/m2e/releases/1.4" target="_blank">Maven</a> 
  	- <a href="http://cucumber.github.io/cucumber-eclipse/update-site/" target="_blank">Cucumber</a>

## Setting up Example Project

- Install Java and set path.
- Install Maven and set path.
- Clone respective repository or download zip.
	- maven : https://github.com/autognizant/ac-example-project
	
# Documentation
### Writing a test

#### Create a feature file and scenario

The cucumber features goes in the `src/test/resources/features` folder and should have the ".feature" extension.
You can start out by looking at `GoogleSearch.feature`.

```
@search
Feature: Google search feature
As a user, I should able to search any keyword and get results.

@ACAUTO-Scenario1 @smoke
Scenario: Search keyword test
  Given I am on home page
   When I search with keyword "Autognizant"
   Then I should get search results as "Cognizant"
```

#### Create Project Automation Hooks class 

The Project Automation Hooks class enables us to execute the project related common functions before starting the execution of each scenario and after finishing the execution of each scenario. It also executes framework related common functions like initializing driver, perform logging setup, load object repository, capture screenshots.

The step definition classes goes in the `steps` package in `src/test/java/` directory.
You can start out by looking at `ProjectAutomationHooks.java`.

```
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
```

#### Create cucumber step definition classes 

The Project Automation Hooks class goes in the `steps` package in `src/test/java/` directory.
You can start out by looking at `HomePageSteps.java`.

```
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
```

#### Create Page Object Model classes 

The Page Object Model classes goes in the `pages` package in `src/test/java/` directory.
You can start out by looking at `HomePage.java`.

```
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
```

#### Create object repository

The Object Repository is created using json files with specific format for storing locator information.
The step definition classes goes in the `pages` package in `src/test/java/ObjectRepository` directory.
You can start out by looking at `HomePage.json`.

```
[
	{
		"webElementName": "Google Image",
		"webElementType": "Image",
		"frameName": "Main",
		"English": {
			"locatorType": "xpath",
			"locatorValue": "//img[@alt='Google']"
		}
	},
	{
		"webElementName": "Google Search Button",
		"webElementType": "Button",
		"frameName": "Main",
		"English": {
			"locatorType": "name",
			"locatorValue": "btnK"
		}
	}
]
```

### Prepare test data

The test data file goes in the `src/test/resources/TestData` directory 
Create a separate excel sheet for scenarios that require test data. The sheet name must be `scenarioUniqueID`.

![image](https://user-images.githubusercontent.com/12494447/167130325-f63a8a7f-94a5-4d8b-8a34-413769c90384.png)

### Set test execution parameters

- Framework level parameters are initialized in `autognizant.config` file in the `src/test/resources/config` directory

```
SCENARIO_ID_PREFIX=@ACAUTO-    (It is used to identify each scenario uniquely and can be mapped with manual test)
APP_WINDOW_TITLE=Google        (It is used to mention the main application window title)
ELEMENT_WAIT=30                (This is an explicit wait timeout in seconds)
```

- Test execution parameters are initialized in `configuration.properties` file in the `src/test/resources/config` directory

```
PROJECT=Google Search Engine
CLIENT_NAME=Google
APPLICATION_TYPE=WEB                        (supported values are WEB,SERVICES)
APPLICATION_URL=https://www.google.com/
ENVIRONMENT=QA
BROWSER=Google Chrome                       (supported values are Internet Explorer,Google Chrome, Mozilla Firefox)
EXECUTION_TYPE=LOCAL                        (supported values are LOCAL,GRID)
GRID_URL=http://localhost:4444/wd/hub
EXECUTION_MODE=REAL                         (supported values are REAL,HEADLESS)
LANGUAGE = ENGLISH

#DB Details
DB_DRIVER=com.mysql.cj.jdbc.Driver
DB_CONNECTION_URL=jdbc:mysql://localhost:3306/employee
DB_USERNAME=root
DB_PASSWORD=root

#SFTP Server Details
SFTP_HOST=localhost
SFTP_PORT=22
SFTP_USERNAME=test
SFTP_PASSWORD=test123
```

### Set up runner class

The runner class enables us to execute different feature files and scenario tags.
You can start out by looking at `RunCucumberTest.java`.

```
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
```
	
### Running tests

Go to your project directory from terminal and hit following commands
* Update autognizant.config file(src/test/resources/config) to configure framework level properties.
* Update configuration.properties file(src/test/resources/config) to configure execution parameters for test run.

```
* mvn clean verify (defualt will run cucumber features/tags methioned RunCucumberTest.java).
* mvn clean verify -Dcucumber.filter.tags="@ACAUTO-Scenario1" (this will run specified cucumber tags).
* mvn clean verify -Dcucumber.features="src/test/resources/features/login.feature" (this will run specified cucumber features).
```

### Execution results

The report is generated inside the `target/cucumber-html-reports` directory. Open `overview-features.html` file to view the reports

## Appendix

### Building

Autognizant requires Java >= 16 and Maven >= 3.8.1.

### License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.	
