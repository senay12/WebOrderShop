package Test;

import Utilities.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PretashopWeporder  extends TestBase {
    @Test
    public void login() {
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        WebElement username = driver.findElement(By.id("ctl00_MainContent_username"));
        username.sendKeys("Tester");
        WebElement password = driver.findElement(By.id("ctl00_MainContent_password"));
        password.sendKeys("test");
        WebElement submit = driver.findElement(By.id("ctl00_MainContent_login_button"));
        submit.click();
        driver.manage().window().maximize();
    }

    @Test
    public void products() throws InterruptedException {
        //1. Login
        login();
        //2. Click on View All Products Link
        Thread.sleep(2000);
        WebElement viewAllProducts = driver.findElement(By.linkText("View all products"));
        viewAllProducts.click();

//3. Remember all the product names from the table
        List<WebElement> productNames = driver.findElements(By.xpath("//table[@class='ProductsTable']/tbody/tr/td[1]"));
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < productNames.size(); i++) {
            list.add(productNames.get(i).getText());
            System.out.println(productNames.get(i).getText());
        }
        // 4.//click on veiw all order link
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Order')]")).click();
        //5. Verify that all the values in the Products column match the names from step 4

        List<WebElement> allProduct = driver.findElements(By.xpath("//table[@class='SampleTable']/tbody/tr/td[3]"));
        ArrayList<String> sampleTableName = new ArrayList<String>();
        for (WebElement el : allProduct) {
            sampleTableName.add(el.getText());
        }

        Assert.assertTrue(sampleTableName.containsAll(list));
    }

    public static int getRandomInteger(int max, int min) {
        return ((int) (Math.random() * (max - min))) + min;

    }

    @Test
    public void createOrder() {
        login();
        driver.findElement(By.linkText("Order")).click();
        //select a product (select diffreent product evertime
        Select select = new Select(driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct")));
        WebElement quantityField = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity"));
        quantityField.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(getRandomInteger(10, 1)));
        WebElement discountField = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtDiscount"));


        switch (getRandomInteger(3, 1)) {
            case 1:
                select.getFirstSelectedOption();
                discountField.sendKeys(Keys.chord(Keys.CONTROL, "a"), "0.08");
                System.out.println("Random number is 1 and " + select.getFirstSelectedOption().getText() + " was chosen");
                break;
            case 2:
                select.selectByIndex(1);
                discountField.sendKeys(Keys.chord(Keys.CONTROL, "a"), "0.15");
                break;
            case 3:
                select.selectByIndex(2);
                discountField.sendKeys(Keys.chord(Keys.CONTROL, "a"), "0.1");
                break;


        }
        WebElement customername = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName"));
        WebElement street = driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2"));
        WebElement city = driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3"));
        WebElement state = driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4"));
        WebElement zip = driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5"));
        WebElement cardType = driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_1"));
        WebElement cardNumber = driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6"));
        WebElement expireDate = driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1"));


        //Faker information
        String fakeCustomName = faker.name().fullName();
        String fakeStreet = faker.address().streetAddress();
        String fakeCity = faker.address().city();
        String fakeState = faker.address().state();
        String fakeZip = faker.address().zipCode();
        String fakeCardNum = faker.number().digits(16);
        String fakeExpireDate = "0" + faker.number().numberBetween(1, 12) + "/" + faker.number().numberBetween(19, 23);

        customername.sendKeys(fakeCustomName);
        street.sendKeys(fakeStreet);
        city.sendKeys(fakeCity);
        state.sendKeys(fakeState);
        zip.sendKeys(fakeZip);
        cardType.click();
        cardNumber.sendKeys(fakeCardNum);
        expireDate.sendKeys(fakeExpireDate);
        driver.findElement(By.linkText("Process")).click();

    }

    @Test
    public void delete() {
        login();
        //Delete a random entry from the table
        //random  8th with name "Samuel Clemens"

        //driver.findElement(By.linkText("View all orders")).click();
        List<WebElement> allRowsBefore =driver.findElements(By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr"));

        WebElement samuelClemens=driver.findElement(By.id("\"ctl00_MainContent_orderGrid_ctl08_OrderSelector"));

         samuelClemens.click();

          driver.findElement(By.id("ctl00_MainContent_btnDelete")).click();

        List<WebElement> allRowsAfter =driver.findElements(By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr"));
        List<WebElement> allNames =driver.findElements(By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr/td[2]"));
        List<String> allNamesText =new ArrayList<String>();
        for(WebElement name : allNames){
            allNamesText.add(name.getText());
        }

     System.out.println(allNamesText);
        Assert.assertFalse(allNamesText.contains("Samuel Clemens"));

        //4.Verify that table row count decreased by 1

        System.out.println("Number of rows before deleting: "+allRowsBefore.size()+" Number of rows after: "+allRowsAfter.size());
        Assert.assertEquals(allRowsAfter.size(), allRowsBefore.size()-1);







    }

}






























