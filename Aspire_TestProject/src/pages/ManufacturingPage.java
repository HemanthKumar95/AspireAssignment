package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ManufacturingPage {

	static WebDriver driver;

	// locators of page elements involved in creation of manufacturing order page
	private static By createButton = By.xpath("//button[contains(text(),'Create')]");
	private static By productName = By.cssSelector("div[name='product_id'] input");
	private static By addALineLink = By.xpath("//div[contains(@class,'active')]//a[text()='Add a line']");
	private static By tableProductName = By.cssSelector("table.table div[name='product_id'] input");
	public static String option = "//li//a[contains(text(),'%s')]";
	private static By orderQuantity = By.cssSelector("input[name='product_uom_qty']");
	private static By saveButton = By.xpath("//button[contains(text(),'Save')]");
	private static By editButton = By.xpath("//button[contains(text(),'Edit')]");
	private static By confirmButton = By.cssSelector("button[name='action_confirm']");
	private static By markAsDoneButton = By.xpath("//span[text()='Mark as Done']");
	private static By applyButton = By.cssSelector("button[name='process']");
	private static By productNameMO = By.cssSelector("a[name='product_id'] span");

	public ManufacturingPage(WebDriver driver) {
		ManufacturingPage.driver = driver;
	}

	public static void createManufacturingOrder(String productName, String quantity) {
		// In this method, we are creating manufacturing order using product name and
		// quantity passed as parameters

		try {

			// creation of Manufacturing order
			driver.findElement(createButton).click();
			driver.findElement(ManufacturingPage.productName).sendKeys(productName);
			driver.findElement(By.xpath(String.format(option, productName))).click();
			driver.findElement(saveButton).click();
			new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(saveButton));

			// creation of bill order
			driver.findElement(editButton).click();
			driver.findElement(addALineLink).click();
			driver.findElement(tableProductName).sendKeys(productName);
			driver.findElement(By.xpath(String.format(option, productName))).click();
			driver.findElement(orderQuantity).clear();
			driver.findElement(orderQuantity).sendKeys(quantity);
			driver.findElement(saveButton).click();

			// confirmation of order
			new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(saveButton));
			driver.findElement(confirmButton).click();

		} catch (Exception e) {
			Assert.fail("Unable to create manufactoring order due to " + e.getMessage());
		}
	}

	public static void markOrderAsDone() {
		// Once the order is made and confirmed, we are marking the order is
		// completed/done
		try {
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(markAsDoneButton));
			driver.findElement(markAsDoneButton).click();
			driver.findElement(applyButton).click();
		} catch (Exception e) {
			Assert.fail("Unable to mark order as done " + e.getMessage());
		}
	}

	public static void verifyMO(String ExpectedproductName) {
		// Once the manufacturing order is created, we verify the details of order
		try {
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(productNameMO));
			String productName = driver.findElement(productNameMO).getText();
			Assert.assertEquals(ExpectedproductName, productName);
		} catch (Exception e) {
			Assert.fail("Unable to verify manufacturing order details " + e.getMessage());
		}
	}

}
