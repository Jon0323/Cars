# Cars

The two major files in this project where I wrote the code and test for this are under this:
Cars/src/main/java/Pages/CarsHome.java
Cars/src/main/java/Tests/TestCases.java

To get started, you will need to install an IDE, I used Intellj:
https://www.jetbrains.com/idea/download/#section=windows

We also need Selenium

For instructions on Selenium setup:
https://www.guru99.com/intellij-selenium-webdriver.html

In order to get this test to work you will need a webdriver

Firefox: https://github.com/mozilla/geckodriver/releases
Chrome: https://chromedriver.chromium.org/
Internet Explorer: https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver
Edge: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/

This project is set up for firefox, but can easily be modified for the others, details of how to point your driver bellow

Make sure you also have your browser installed

Next, we need to import the project, for detailed instructions on how to import a project from git into intellij:
https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html

Importatnt: in TestCases.java, there are hardcoded address to files listed above, change those to the correct one for local setup

Find this line: static String driverPath = "C:\\Users\\Jon\\Documents\\geckodriver.exe";
*This will need to be changed to where you driver is installed

Also if you want to change browers, you will need to make some additinal changes:
In Setup() in TestCases.java, System.setProperty("webdriver.gecko.driver", driverPath); changes to System.setProperty("webdriver.[insertDriverhere].driver", driverPath)

Right under CarsHome.java in the class variables, this line will need to be changed:
FirefoxDriver driver = new FirefoxDriver();
for example if using Chrome, this will need to be changed to WebDriver driver = new ChromeDriver();

 

For this line: static String screenShotPath = "C:\\Users\\Jon\\Documents\\CarsScreenshots";
*Change this to a place on your desktop you would like to store the screenshot taken 
