package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage {
	
	static WebDriver driver;
	
	//locators of elements present in HomePage
	public static By discussLink = By.cssSelector("a#result_app_0");
	public static By inventoryLink = By.cssSelector("a#result_app_1");
	public static By manufacturingLink = By.cssSelector("a#result_app_2");
	public static By BarcodeLink = By.cssSelector("a#result_app_3");

	public HomePage(WebDriver driver) {
		HomePage.driver = driver;
	}
	
	public static void navigateToSection(By element) {
		//performs navigation to specified section by using element passed as parameter
		try {
			WebElement section = driver.findElement(element);
			String sectionName = section.getText();
			section.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(element));
			Assert.assertTrue(driver.getTitle().contains(sectionName));
		} catch (Exception e) {
			Assert.fail("Unable to navigate to specified section " + e.getMessage());
		}
	}

}
