package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage {

	static WebDriver driver; 
	private static By inputEmail = By.cssSelector("input#login");
	private static By inputPassword = By.cssSelector("input#password");
	private static By loginButton = By.cssSelector("button[type=\"submit\"]");

	public LoginPage(WebDriver driver) {
		LoginPage.driver = driver;
	}

	public static void login(String userEmail, String password) {
		try {
			driver.findElement(inputEmail).sendKeys(userEmail);
			driver.findElement(inputPassword).sendKeys(password);
			driver.findElement(loginButton).click();
		}catch(Exception e) {
			Assert.fail("Error occured during Login "+e.getMessage());
		}
	}
	
}
