package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ManufacturingPage;

public class TestAspire {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://aspireapp.odoo.com/");
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}

	@Test(priority = 1)
	public void testCreateOrder() {
		new LoginPage(driver);
		LoginPage.login("user@aspireapp.com", "@sp1r3app");
		new HomePage(driver);
		HomePage.navigateToSection(HomePage.inventoryLink);
		new InventoryPage(driver);
		InventoryPage.navigateToProducts();
		InventoryPage.createProduct("Creams");
		InventoryPage.navigateToUpdateQuantity();
		InventoryPage.updateQuantity("WH/Stock", "Creams", "50");
	}

	@Test(priority = 2)
	public void testCreateMO() {
		InventoryPage.navigateToApplications();
		HomePage.navigateToSection(HomePage.manufacturingLink);
		new ManufacturingPage(driver);
		ManufacturingPage.createManufacturingOrder("Creams", "2");
		ManufacturingPage.markOrderAsDone();
		ManufacturingPage.verifyMO("Creams");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
