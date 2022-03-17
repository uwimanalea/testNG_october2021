package variousConcepts;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class CrmTest {
	String browser=null;
WebDriver driver;
String url;


//storing webelement using by Class

		By userNameLocator=By.xpath("//input[@id='username']");
		By passwordLocator=By.xpath("//*[@id=\"password\"]");
		By signInButtonLocator=By.xpath("/html/body/div/div/div/form/div[3]/button");
		By dashboardHeaderlocator=By.xpath("//h2[contains(text(),'Dashboard')]");
		By customer_Menu_Locator = By.xpath("//*[@id=\"side-menu\"]/li[3]/a");
		By addCustomerMenuLocator = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
		By add_ContactHead_locator = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
		By Full_name_locator = By.xpath("//*[@id=\"account\"]");
		By Company_droddown_locator = By.xpath("//select[@id=\"cid\"]");
		By email_locator = By.xpath("//*[@id=\"email\"]");
		By COUNTRY_LOCATOR = By.xpath("//select[@id=\"country\"]");
		By phone_locator = By.xpath("//*[@id=\"phone\"]");
		By address_locator = By.xpath("//*[@id=\"address\"]");
		By city_locator = By.xpath("//*[@id=\"city\"]");
		By state_religion_locator = By.xpath("//*[@id=\"state\"]");
		By zip_locator = By.xpath("//*[@id=\"zip\"]");
		By Tags_locator = By.xpath("//*[@id=\"tags\"]");
		By Submit_locator = By.xpath("//*[@id=\"submit\"]");
		By currency_locator = By.xpath("//select[@id=\"currency\"]");
		//By Group_locator = By.xpath("//*[@id=\"add_new_group\"]");
		By group_name_locator = By.xpath("//select[@id=\"group\"]");
		//By save_locator = By.xpath("/html/body/div[1]/div/div/div/div[3]/button");
		By passworld_locator = By.xpath("//*[@id=\"password\"]");
		By comfirmPassword_locator = By.xpath("//*[@id=\"cpassword\"]");
		
		
		
@BeforeTest
public void readConfig() {
	//fileReader//scanner //InPutStream //bufferedReader
// to manage file reader we use try and catch
	
	try {
		InputStream input = new FileInputStream("src\\main\\java\\config\\congig.properties");
		Properties prop = new Properties();
		prop.load(input);
		prop.getProperty("browser");
		 browser =prop.getProperty("browser");
		 System.out.println("browser used= " + browser);
		
		url= prop.getProperty("url");
		 
		 
	} catch (IOException e) {
		e.printStackTrace();
	}

}


@BeforeMethod
public void init() {
	
	if(browser.equalsIgnoreCase("chrome")) {
		System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
		driver= (WebDriver) new ChromeDriver();
		
	}else if(browser.equalsIgnoreCase("firefox")){
//		   set up firefox
		System.setProperty("webdriver.gecko.driver","driver\\geckodriver.exe");
		driver =new FirefoxDriver();
	}
	

	
	driver.manage().deleteAllCookies();
	driver.get(url);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	
}
@Test (priority = 1)
	public void loginTest() {		
		
		driver.findElement(userNameLocator).sendKeys("demo@techfios.com");
		driver.findElement(passwordLocator).sendKeys("abc123");
		driver.findElement(signInButtonLocator).click();

		//Assert.assertTrue("Dashboard page not found!!!", driver.findElement(dashboardHeaderlocator).isDisplayed());
		String dashboarHeader = driver.findElement(dashboardHeaderlocator).getText();
		System.out.println(dashboarHeader);
		//Assert.assertEquals(dashboarHeader, "dashboard1", "wrong page");
		

}
@Test(priority = 2)
public void addCustomerTest() {
	
	loginTest();
	driver.findElement(customer_Menu_Locator).click();
driver.findElement(addCustomerMenuLocator).click();

WebDriverWait wait = new WebDriverWait(driver, 5);
wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(add_ContactHead_locator));


Assert.assertEquals(driver.findElement(add_ContactHead_locator).getText(),"Add Contact", "wrong page");


driver.findElement(Full_name_locator).sendKeys("selenium october" + generateRandom(9999));
selectFromDropdown(Company_droddown_locator,"Techfios");
driver.findElement(email_locator).sendKeys(generateRandom(999) + "demo@techfios.com");
driver.findElement(phone_locator).sendKeys(generateRandom(99)+ "912211");
driver.findElement(address_locator).sendKeys("12th steret");
driver.findElement(city_locator).sendKeys("puyallup");
driver.findElement(state_religion_locator).sendKeys("washington");
driver.findElement(zip_locator).sendKeys(generateRandom(9) + "98375");
driver.findElement(Tags_locator).sendKeys("add");
selectFromDropdown(COUNTRY_LOCATOR, "United Arab Emirates");
selectFromDropdown(currency_locator, "USD");
/*
 * driver.findElement(Group_locator).click();
 * driver.findElement(group_name_locator).sendKeys("world");
 */
selectFromDropdown(group_name_locator, "world");
//driver.findElement(save_locator).click();
driver.findElement(passwordLocator).sendKeys("abc123");
driver.findElement(comfirmPassword_locator).sendKeys("abc123");
driver.findElement(Submit_locator).click();

}

private void selectFromDropdown(By locator, String visibleText) {
	Select sel = new Select(driver.findElement(locator));
	sel.selectByVisibleText(visibleText);
	
}


public int generateRandom(int bounderyNum) {
	Random rnd = new Random();
	int generatedNum= rnd.nextInt(bounderyNum);
	return generatedNum;
	

}

//@AfterMethod
public void tearDown() {
	driver.close();
	driver.quit();
}
}

