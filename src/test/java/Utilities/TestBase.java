package Utilities;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

public abstract   class TestBase{

    protected WebDriver driver;
    protected SoftAssert softAssert;
    protected Actions actions;
    protected Faker faker;

    @BeforeClass
    public void setUpClass(){
        WebDriverManager.chromedriver().setup();
        faker = new Faker();
    }

    @BeforeMethod
    public void setUpMethod(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void tearDownMethod() throws InterruptedException {
        Thread.sleep(10000);
        // driver.quit();
        softAssert.assertAll();
        actions = new Actions(driver);
    }


}