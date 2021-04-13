package Tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import Pages.CarsHomePage;

import java.io.IOException;


public class TestCases {

    //Basic setup, I'm loading the selenium gecko (firefox) package on my pc
    static String driverPath = "C:\\Users\\Jon\\Documents\\geckodriver.exe"; //change this to where your driver is located
    static String websitePath = "https://www.cars.com/";
    static String screenShotPath = "C:\\Users\\Jon\\Documents\\CarsScreenshots";  //change this to where you would like
    static CarsHomePage home;

    //This is where we are going put the data we are going to test
    static String newUsed = "Used";
    static String make = "Honda";
    static String model = "Pilot";
    static int maxPrice = 50000;
    static int distance = 100;
    static int zipCode = 60008;


    @BeforeClass
    public static void setup() {

        System.setProperty("webdriver.gecko.driver", driverPath);

        //Here we initialize all the pages
        home = new CarsHomePage(websitePath);

        home.searchForCar(newUsed, make, model, maxPrice, distance, zipCode);
    }

    @Test
    public void checkMaxPrice()
    {
        Assert.assertTrue(home.GetMaxPriceFilter(maxPrice));
    }

    @Test
    public void checkMake()
    {
        Assert.assertTrue(home.GetFilterData(make));
    }

    @Test
    public void checkModel()
    {
        Assert.assertTrue(home.GetFilterData(model));
    }

    @Test
    public void checkNewOrUsed()
    {
        Assert.assertTrue(home.GetFilterData(newUsed));
    }

    @Test
    public void changeToNew()
    { 
        home.changeRadioButtonNew("New");
        Assert.assertTrue(home.GetFilterData("New") && !home.GetFilterData("Used"));
    }

    @Test
    public void checkTurning()
    {
        home.clickCheckboxOption("Touring 8-Passenger");
        Assert.assertTrue(home.GetFilterData("Touring 8-Passenger"));
    }

    @Test
    public void checkCarTitle()
    {
        //home.clickListedCar(2); //We want the second item in the list
        Assert.assertTrue(home.carTitlePresent(make,model,"8-Passenger"));
    }

    @Test
    public void checkAvailabilityButton()
    {
        Assert.assertTrue(home.availabilityButtonPresent());
    }

    @AfterClass
    public static void afterTests() {
        home.contactSeller("Car", "Owner", "carowner@yahoo.com");
        home.scrollToContactSeller();
        try {
            home.takeScreenshot(screenShotPath);
        }
        catch (IOException ex)
        {
            System.out.println("Save Screenshot Failed");
        }

        //finally we close out the webpage
        home.closeWebPage();
    }

}
