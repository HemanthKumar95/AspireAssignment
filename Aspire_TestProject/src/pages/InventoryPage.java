package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class InventoryPage {
	static WebDriver driver;

	public static By applicationsButton = By.cssSelector("a[title='Applications']");
	public static By headerproductsLink = By.xpath("//a[contains(text(),'Products')]");
	public static By productsLink = By.xpath("//span[contains(text(),'Products')]//parent::a");
	public static By headertitle = By.cssSelector("li.breadcrumb-item");

	public static By createButton = By.xpath("//button[contains(text(),'Create')]");
	public static By inputProductName = By.cssSelector("input[name='name']");
	public static By inputCost = By.cssSelector("div[name='standard_price'] input");
	public static By saveButton = By.xpath("//button[contains(text(),'Save')]");
	public static By productNameAfterCreation = By.cssSelector("h1 span");

	public static By updateQuatityButton = By.xpath("//span[contains(text(),'Update Quantity')]");
	public static By inputLocation = By.cssSelector("div[name='location_id'] input");
	public static String option = "//li//a[contains(text(),'%s')]";
	public static By inputPackage = By.cssSelector("div[name='package_id'] input");
	public static By inputQuantity = By.cssSelector("input[name='inventory_quantity']");
	public static String productLocation = "td[title='%s']";

	public InventoryPage(WebDriver driver) {
		InventoryPage.driver = driver;
	}

	@SuppressWarnings("deprecation")
	public static void navigateToProducts() {
		driver.findElement(headerproductsLink).click();
		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(productsLink));
		String name = driver.findElement(productsLink).getText();
		driver.findElement(productsLink).click();
		new WebDriverWait(driver, 3).until(ExpectedConditions.textToBePresentInElement(headertitle, name));
		Assert.assertTrue(driver.getTitle().contains(name));
	}

	public static void createProduct(String productName){
		driver.findElement(createButton).click();
		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(createButton));
		driver.findElement(inputProductName).sendKeys(productName);
		driver.findElement(saveButton).click();
		new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(saveButton));
	}

	public static void verifyCreationOfProduct(String productName) {

		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(updateQuatityButton));
		Assert.assertEquals(driver.findElement(productNameAfterCreation).getText(), productName);
	}

	public static void navigateToUpdateQuantity() {

		driver.findElement(updateQuatityButton).click();
		new WebDriverWait(driver, 3).until(ExpectedConditions.titleContains("Update Quantity"));
		driver.findElement(createButton).click();
		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(inputLocation));
	}

	public static void updateQuantity(String location, String packageName, String quantity) {

		driver.findElement(inputLocation).click();
		driver.findElement(By.xpath(String.format(option, location))).click();
		driver.findElement(inputPackage).sendKeys(packageName + Keys.TAB);
		driver.findElement(inputQuantity).clear();
		driver.findElement(inputQuantity).sendKeys(quantity);
		driver.findElement(saveButton).click();
		boolean isLocationVisible = driver.findElement(By.cssSelector(String.format(productLocation, location)))
				.isDisplayed();
		Assert.assertTrue(isLocationVisible);
	}
	
	public static void navigateToApplications() {
		
		driver.findElement(applicationsButton).click();
		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(HomePage.discussLink));

	}
}








