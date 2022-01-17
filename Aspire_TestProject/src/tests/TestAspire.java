package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ManufacturingPage;

public class TestAspire {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		//This method opens the chrome browser and navigates to login page of Aspire. 
		//and apply an implicit timeout of 5 seconds throughout the code
		System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://aspireapp.odoo.com/");
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}

	@Test(priority = 1)
	@Parameters({"userEmail","password"})  
	public void testCreateOrder(String userEmail, String password) {
		//perform the login process with the given credentials
		//we can also pass credentials from testng.xml using parameter annotation
		new LoginPage(driver);
		LoginPage.login(userEmail, password);
		
		//post login, we are navigated to Inventory page.
		new HomePage(driver);
		HomePage.navigateToSection(HomePage.inventoryLink);
		
		//Here, we are navigate to products page to create new Product
		new InventoryPage(driver);
		InventoryPage.navigateToProducts();
		
		//creation of new Product is performed using below method
		InventoryPage.createProduct("Creams");
		
		//post the creation of product, we try to update quantity of newly created product
		InventoryPage.navigateToUpdateQuantity();
		InventoryPage.updateQuantity("WH/Stock", "Creams", "50");
	}

	@Test(priority = 2)
	public void testCreateMO() {
		// we are navigated back to applications page
		InventoryPage.navigateToApplications();
		
		// we are navigated to manufacturing page of Aspire
		HomePage.navigateToSection(HomePage.manufacturingLink);
		new ManufacturingPage(driver);
		
		// we are using the below method to create a manufacturing order for the newly created product 
		ManufacturingPage.createManufacturingOrder("Creams", "2");
		
		// after the creation of product, mark the order as Done
		ManufacturingPage.markOrderAsDone();
		
		//verify whether the MO for new order is created or not.
		ManufacturingPage.verifyMO("Creams");
	}

	@AfterClass
	public void afterClass() {
		
		//closes the browser
		driver.quit();
	}
}
