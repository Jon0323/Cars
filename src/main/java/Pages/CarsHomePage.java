package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.edge.EdgeDriver;  //Enable this if you want to use Edge browser
//import org.openqa.selenium.ie.InternetExplorerDriver; //Enable this if you want to use Internet Explorer browser
//import org.openqa.selenium.chrome;  //Enable this if you want to use Chrome browser
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CarsHomePage {

    FirefoxDriver driver = new FirefoxDriver();

    //Constructor
    public CarsHomePage(String webSitPath)
    {
        this.driver.get(webSitPath);
    }

    //All the locators for the search fields
    By newOrUsed = By.name("stockType");
    By makeOfCar = By.name("makeId");
    By modelOfCar = By.name("modelId");
    By priceOfCar = By.name("priceMax");
    By distanceOfCar = By.name("radius");
    By zipCodeOfCar = By.name("zip");
    By searchButton = By.className("NZE2g");


    ///searchForCar(New or used, make, model, price, distance, zip code)
    public void searchForCar(String newUsed, String make, String model, int price, int distance, int zip)
    {
        //In this section we convert everything to the right format
        //convert price to correct format
        String priceText = convertToDollars(price);
        //For this variable, new or used needs an additional " Cars" at the end of the string
        newUsed = newUsed + " Cars";
        //Zip code needs an addition at the end as well
        String distanceText = String.valueOf(distance) + " Miles from";
        String zipText = String.valueOf(zip);

        Select newUsedDrp = new Select(driver.findElement(newOrUsed));
        newUsedDrp.selectByVisibleText(newUsed);

        Select makeDrp = new Select(driver.findElement(makeOfCar));
        makeDrp.selectByVisibleText(make);

        Select modelDrp = new Select(driver.findElement(modelOfCar));
        modelDrp.selectByVisibleText(model);

        Select priceDrp = new Select(driver.findElement(priceOfCar));
        priceDrp.selectByVisibleText(priceText);

        Select distanceDrp = new Select(driver.findElement(distanceOfCar));
        distanceDrp.selectByVisibleText(distanceText);

        WebElement zipField = driver.findElement(zipCodeOfCar);
        zipField.clear();
        zipField.sendKeys(zipText);

        driver.findElement(searchButton).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    public boolean GetMaxPriceFilter(int price)
    {
        String priceString = convertToDollars(price);
        String locatorText = "//header[@class='filter-header']//label[contains(text(),'Maximum Price: ";
        locatorText += priceString + "')]";
        By maxPriceFilter = By.xpath(locatorText);

        return driver.findElements(maxPriceFilter).size() == 1;

    }

    public boolean GetFilterData(String carInfo)
    {
        String locatorText = "//header[@class='filter-header']//label[contains(text(),'";
        locatorText += carInfo + "')]";
        By filterInfoLocator = By.xpath(locatorText);

        return driver.findElements(filterInfoLocator).size() == 1;
    }

    public void changeRadioButtonNew(String option)
    {
        String locatorText = "//label[@class='radio__label'][contains(text(),'";
        locatorText += option + "')]";
        By radioButton = By.xpath(locatorText);
        driver.findElement(radioButton).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    public void clickCheckboxOption(String option)
    {
        String locatorText =  "label[@class='checkbox__label'][contains(text(),'";
        locatorText += option + "')]";
        By checkBoxSelected = By.xpath(locatorText);
        driver.findElement(checkBoxSelected).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    //This function will click when listed
    public void clickListedCar(int carNumber)
    {
        By carList = By.className("listing-row__title");
        List<WebElement> cars = driver.findElements(carList);
        cars.get(carNumber - 1).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public boolean carTitlePresent(String make, String model, String type)
    {
        By carTitle = By.xpath("//h1[@class='cui-heading-2--secondary vehicle-info__title']");
        WebElement nameOfCar = driver.findElement(carTitle);
        String carText = nameOfCar.getText();

        System.out.println("Name of Car: " + carText);

        if(carText.contains(make) && carText.contains(model) && carText.contains(type))
        {
            return true;
        }
        else {
            System.out.println("We Returned False");
            return false;
        }
    }

    public boolean availabilityButtonPresent()
    {
        By availabilityButton = By.xpath("//button[@name='submit']");
        return driver.findElements(availabilityButton).size() > 0;
    }

    public void contactSeller(String firstName, String lastName, String email)
    {
        //We need three locators for this section
        By firstNameLocator = By.xpath("//input[@placeholder='First Name']");
        By lastNameLocator = By.xpath("//input[@placeholder='Last Name']");
        By emailLocator = By.xpath("//input[@placeholder='Email']");

        driver.findElement(firstNameLocator).sendKeys(firstName);
        driver.findElement(lastNameLocator).sendKeys(lastName);
        driver.findElement(emailLocator).sendKeys(email);
    }

    public void scrollToContactSeller()
    {
        //We need the locator for the contact seller info
        By contactLocator = By.xpath("//h2[@class='page-section__title--sub cui-heading-2 calculator-title']");
        WebElement sellerBox = driver.findElement(contactLocator);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", sellerBox);
    }

    public void takeScreenshot(String filepath) throws IOException {
        //In this part, we will take today's date and use that as part of our filename
        String out = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date());
        filepath += "\\" + out +"ScreenShot.png";

        TakesScreenshot scrShot = ((TakesScreenshot)driver);
        File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(filepath);

        FileUtils.copyFile(scrFile,destFile);
    }

    public void closeWebPage()
    {
        driver.close();
    }

    //Helper method to convert int to dollar amount
    private String convertToDollars(int money)
    {
        //convert price to correct format
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String priceText = format.format(money);
        priceText = priceText.substring(0, priceText.length() -3); //shave off the .00
        return priceText;
    }

}
