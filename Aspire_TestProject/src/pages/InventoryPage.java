package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class InventoryPage {
	static WebDriver driver;

	// locators of page elements in Inventory page
	private static By applicationsButton = By.cssSelector("a[title='Applications']");
	private static By headerproductsLink = By.xpath("//a[contains(text(),'Products')]");
	private static By productsLink = By.xpath("//span[contains(text(),'Products')]//parent::a");
	private static By headertitle = By.cssSelector("li.breadcrumb-item");

	// locators of page elements involved in creation of new Product
	private static By createButton = By.xpath("//button[contains(text(),'Create')]");
	private static By inputProductName = By.cssSelector("input[name='name']");
	private static By saveButton = By.xpath("//button[contains(text(),'Save')]");
	private static By productNameAfterCreation = By.cssSelector("h1 span");

	// locators of page elements involved in updating quantity of product
	private static By updateQuatityButton = By.xpath("//span[contains(text(),'Update Quantity')]");
	private static By inputLocation = By.cssSelector("div[name='location_id'] input");
	private static String option = "//li//a[contains(text(),'%s')]";
	private static By inputPackage = By.cssSelector("div[name='package_id'] input");
	private static By inputQuantity = By.cssSelector("input[name='inventory_quantity']");
	private static String productLocation = "td[title='%s']";

	public InventoryPage(WebDriver driver) {
		InventoryPage.driver = driver;
	}

	@SuppressWarnings("deprecation")
	public static void navigateToProducts() {
		// we are navigated from Inventory home page to products page
		// title is verified to check the navigation
		try {
			driver.findElement(headerproductsLink).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(productsLink));
			String name = driver.findElement(productsLink).getText();
			driver.findElement(productsLink).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.textToBePresentInElement(headertitle, name));
			Assert.assertTrue(driver.getTitle().contains(name));
		} catch (Exception e) {
			Assert.fail("Unable to navigate to Products section " + e.getMessage());
		}
	}

	public static void createProduct(String productName) {
		// this method is used to create new product with product name passed as
		// parameter
		try {
			driver.findElement(createButton).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(createButton));
			driver.findElement(inputProductName).sendKeys(productName);
			driver.findElement(saveButton).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(saveButton));
		} catch (Exception e) {
			Assert.fail("Error occured during creation of product " + e.getMessage());
		}
	}

	public static void verifyCreationOfProduct(String productName) {
		// this method is used to verify whether the product is created or not
		try {
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(updateQuatityButton));
			Assert.assertEquals(productName, driver.findElement(productNameAfterCreation).getText());
		} catch (Exception e) {
			Assert.fail("Unable to verify product name post creation " + e.getMessage());
		}
	}

	public static void navigateToUpdateQuantity() {
		// Using this method, we navigate to update quantity page
		try {
			driver.findElement(updateQuatityButton).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.titleContains("Update Quantity"));
			driver.findElement(createButton).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(inputLocation));
		} catch (Exception e) {
			Assert.fail("Unable to navigate to Update quantity page " + e.getMessage());
		}
	}

	public static void updateQuantity(String location, String packageName, String quantity) {
		// In this method, we try to update the quantity of newly created product name
		try {
			driver.findElement(inputLocation).click();
			driver.findElement(By.xpath(String.format(option, location))).click();
			driver.findElement(inputPackage).sendKeys(packageName + Keys.TAB);
			driver.findElement(inputQuantity).clear();
			driver.findElement(inputQuantity).sendKeys(quantity);
			driver.findElement(saveButton).click();
			boolean isLocationVisible = driver.findElement(By.cssSelector(String.format(productLocation, location)))
					.isDisplayed();
			Assert.assertTrue(isLocationVisible);
		} catch (Exception e) {
			Assert.fail("Unable to update quantity of " + packageName + e.getMessage());
		}
	}

	public static void navigateToApplications() {
		// navigates back to Applications page
		try {
			driver.findElement(applicationsButton).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(HomePage.discussLink));
		} catch (Exception e) {
			Assert.fail("Unable to navigate to Applications page " + e.getMessage());
		}
	}
}
