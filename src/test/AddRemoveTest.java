import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddRemoveTest {

    public WebDriver driver;

    @BeforeTest
    public void driverSetupCategoryAndItemPickUpAndAddToTheCart() {
        WebDriverManager.chromedriver().browserVersion("97").setup();
        driver = new ChromeDriver();
        driver.get("https://www.amazon.com/");

        WebElement category = driver.findElement(By.xpath("//a[@aria-label = \"Toys & Games\"]"));
        category.click();

        List<WebElement> items = new ArrayList<>(driver.findElements(By.xpath("//div[contains(@class,\"SearchResultsContainer\")]//img")));
        int index = getIndex(items.size());
        WebElement selectedItem = items.get(index);
        selectedItem.click();

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
        addToCartButton.submit();
    }

    @AfterTest
    public void driverClose() {
        driver.close();
        driver.quit();
    }


    @Test
    public void addToCartTest() {
        WebElement addedToCartConfirmation = driver.findElement(By.xpath("//div[contains(@class, \"sw-atc-message-section\")]//span[normalize-space(text()=\"Added to Cart\")]"));
        Assert.assertEquals(addedToCartConfirmation.getText(), "Added to Cart");

        WebElement numberOfItemsInTheCart = driver.findElement(By.id("nav-cart-count"));
        Assert.assertEquals(Integer.valueOf(numberOfItemsInTheCart.getText()), 1);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void removeFromCartTest() {
        WebElement cart = driver.findElement(By.id("nav-cart"));
        cart.click();

        WebElement deleteButton = driver.findElement(By.xpath("//input[@value =\"Delete\"]"));
        deleteButton.click();

        WebElement cartIsEmptyConfirmation = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"a-row sc-cart-header\"]//h1")));
        Assert.assertEquals(cartIsEmptyConfirmation.getText(), "Your Amazon Cart is empty.");

        WebElement priceOfItemInTheCart = driver.findElement(By.xpath("//span[text()=\"$0.00\"]"));
        Assert.assertEquals(priceOfItemInTheCart.getText(), "$0.00");

    }

    private int getIndex(int index) {
        Random random = new Random();
        return random.nextInt(index + 1);
    }
}
