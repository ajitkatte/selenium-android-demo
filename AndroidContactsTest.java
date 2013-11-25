package com.testing.example;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This test class automate few scenario for android contacts app.
 * Use TestNG to run this class or you can unit testing framework also. 
 *
 */

public class AndroidContactsTest {
    private WebDriver driver;
    @BeforeMethod
    public void setUp() throws Exception {
        // set up appium
    	//Set up desired capabilities and pass the Android app-activity and app-package to Appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
		capabilities.setCapability(CapabilityType.VERSION, "4.3");
		capabilities.setCapability(CapabilityType.PLATFORM, "Windows");
		capabilities.setCapability("app-package", "com.android.contacts"); // This is package name of your app (you can get it from apk info app)
		capabilities.setCapability("app-activity", "com.android.contacts.activities.PeopleActivity"); // This is Launcher activity of your app (you can get it from apk info app)
		//	Create RemoteWebDriver instance and connect to the Appium server.
		//It will launch the Calculator App in Android Device using the configurations specified in Desired Capabilities
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    // this test case should used when we are adding contact to app for first time.
    @Test
    public void addContact() throws InterruptedException{
        WebElement el = driver.findElement(By.name("Create a new contact"));
        el.click();
        Thread.sleep(5000);
        List<WebElement> textFieldsList = driver.findElements(By.tagName("textfield"));
        textFieldsList.get(0).sendKeys("Some Name");
        textFieldsList.get(2).sendKeys("Some@example.com");
        driver.findElement(By.name("Done")).click();
        driver.findElements(By.tagName("ImageView")).get(0).click();
    }

    //Use when the app has one or more contacts already
   @Test
    public void addContact2() throws InterruptedException{
        WebElement el = driver.findElement(By.name("New"));
        el.click();
        List<WebElement> textFieldsList = driver.findElements(By.tagName("textfield"));
        textFieldsList.get(0).sendKeys("John Paul");
        textFieldsList.get(2).sendKeys("johnpaul@gmail.com");
        driver.findElement(By.name("Done")).click();
        driver.findElements(By.tagName("ImageView")).get(0).click();
        
    }
    
    @Test
    public void testSearchContact()
    {
    	WebElement el = driver.findElement(By.name("Search"));
        el.click();
        
        driver.findElement(By.name("Search query")).sendKeys("Some");
        
        driver.findElement(By.name("Some Name"));
    	
    }
    
    @Test
    public void testClearTextInSearch()
    {
    	WebElement el = driver.findElement(By.name("Search"));
        el.click();
        //enters text to search field 
        driver.findElement(By.name("Search query")).sendKeys("Ajit");
        //clears the text from search field
        driver.findElement(By.name("Clear query")).click();
        
        driver.findElement(By.name("Some Name"));
    	
    }
    
    @Test
    public void testDeleteContact() throws IOException, InterruptedException{
    	WebElement el = driver.findElement(By.name("Search"));
        el.click();
        
        driver.findElement(By.name("Search query")).sendKeys("John");
    	 driver.findElement(By.name("John Paul")).click();
    	// to send menu key
         Process p = Runtime.getRuntime().exec("adb shell input keyevent 82");
         p.waitFor();
         
         driver.findElement(By.name("Delete")).click();
         driver.findElement(By.name("OK")).click();
         driver.findElements(By.name("John Paul"));
         
    }
    
    //to view contacts names by last name
    @Test
    public void testViewContactsNamesByLastName() throws IOException, InterruptedException
    {
    	 // to send menu key 
    	 Process p = Runtime.getRuntime().exec("adb shell input keyevent 82");
         p.waitFor();
         
         driver.findElement(By.name("Settings")).click();
         driver.findElement(By.name("View contact names as")).click();
         driver.findElement(By.name("Last name first")).click();
         driver.findElements(By.tagName("ImageView")).get(0).click();
         driver.findElement(By.name("Search")).click();
         driver.findElement(By.name("Search query")).sendKeys("Some");
    	 driver.findElement(By.name("Name, Some"));
         Process p1 = Runtime.getRuntime().exec("adb shell input keyevent 82");
         p1.waitFor();
         
         driver.findElement(By.name("Settings")).click();
         driver.findElement(By.name("View contact names as")).click();
         driver.findElement(By.name("First name first")).click();
         driver.findElement(By.name("Some Name"));
    }

    
    // to edit contact and add mobile number to it.
    @Test
    public void testEditContactAddMobileNumber() throws IOException, InterruptedException
    {
    	driver.findElement(By.name("Some Name")).click(); 
    	Process p = Runtime.getRuntime().exec("adb shell input keyevent 82");
         p.waitFor();
         
         driver.findElement(By.name("Edit")).click();
         driver.findElement(By.name("Add another field")).click();
         driver.findElement(By.name("Phone")).click();
         driver.findElement(By.name("Mobile")).click();
         driver.findElements(By.tagName("CheckedTextView")).get(0).click();
         driver.findElement(By.name("Phone")).sendKeys("1234567890");
         driver.findElement(By.name("Done")).click();
         driver.findElement(By.name("1 234-567-890"));
         driver.findElements(By.tagName("ImageView")).get(0).click();
         
         
    } 
    
}
