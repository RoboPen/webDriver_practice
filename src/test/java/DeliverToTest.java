import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;


public class DeliverToTest {
    WebDriver driver;

    @BeforeMethod
    public void before() {
        WebDriverManager.chromedriver().browserVersion("98").setup();
        driver = new ChromeDriver();
        driver.get("https://www.amazon.com/");
    }

    @DataProvider(name = "zipCodes")
    public Object[][] zipCodes() {
        return new Object[][]{
                {"Montgomery", "36104"},
                {"Phoenix", "85001"},
                {"Santa Fe", "87501"}
        };
    }

    @Test(dataProvider = "zipCodes")
    public void deliveryZipCodeTest(String cityName, String zipCode) {
        WebElement deliverIcon = driver.findElement(By.id("glow-ingress-block"));
        deliverIcon.click();
        WebElement zipCodeInput = new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.elementToBeClickable(By.id("GLUXZipUpdateInput")));
        WebElement applyZipCodeButton = driver.findElement(By.xpath("//span[text()=\"Apply\"]/.."));
        zipCodeInput.sendKeys(zipCode);
        applyZipCodeButton.click();
        WebElement continueButton = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@id=\"GLUXConfirmClose\"]/../..)[2]")));
        continueButton.click();
        String destinationNameFirst = driver.findElement(By.id("glow-ingress-line2")).getText();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions
                .not(ExpectedConditions
                        .textToBePresentInElement(driver.findElement(By.id("glow-ingress-line2")), destinationNameFirst)));
        WebElement destinationName = driver.findElement(By.id("glow-ingress-line2"));

        Assert.assertTrue(destinationName.getText().contains(cityName));
    }

    @Test
    public void PolandIsPresentOnList() {
        WebElement deliverIcon = driver.findElement(By.id("glow-ingress-block"));
        deliverIcon.click();
        WebElement listDropdown = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.id("GLUXCountryListDropdown")));
        listDropdown.click();
        WebElement polandLink = driver.findElement(By.xpath("//a[text()=\"Poland\"]"));

        Assert.assertTrue(polandLink.isDisplayed());
    }


    @Test
    public void verifyShippingCountry() throws InterruptedException {
        WebElement deliverIcon = driver.findElement(By.id("glow-ingress-block"));
        deliverIcon.click();
        WebElement listDropdown = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.id("GLUXCountryListDropdown")));
        listDropdown.click();
        WebElement countryLink = driver.findElement(By.xpath("//a[text()=\"Austria\"]"));
        countryLink.click();
        WebElement doneButton = driver.findElement(By.xpath("//button[@name=\"glowDoneButton\"]"));
        doneButton.click();
        // need to use Thread.sleep because page is reloading
        Thread.sleep(3000);
        WebElement headsetsCategory = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@alt,\"Headsets\")]/../..")));
        headsetsCategory.click();
        WebElement resultItem = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,\"s-result-item\")][2]//a)[1]")));
        resultItem.click();

        WebElement deliveryInfo = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("contextualIngressPtLabel_deliveryShortLine")));

        Assert.assertTrue(deliveryInfo.getText().contains("Austria"));
    }

    @AfterMethod
    public void after() {
        driver.close();
        driver.quit();
    }
}
