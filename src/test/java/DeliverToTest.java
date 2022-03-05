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
import java.util.List;
import java.util.stream.Collectors;


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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(deliverIcon));
        WebElement destinationName = driver.findElement(By.id("glow-ingress-line2"));

        Assert.assertTrue(destinationName.getText().contains(cityName));
    }

    @Test
    public void polandIsPresentOnList() {
        WebElement deliverIcon = driver.findElement(By.id("glow-ingress-block"));
        deliverIcon.click();
        WebElement listDropdown = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.id("GLUXCountryListDropdown")));
        listDropdown.click();
        List<String> listOfCountries =
                driver.findElements(By.xpath("//ul[contains(@class, \"a-list-link\")]/li"))
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());

        Assert.assertTrue(listOfCountries.contains("Poland"));
    }

    @DataProvider(name = "countries")
    public Object[][] countries() {
        return new Object[][]{
                {"Austria"},
                {"Germany"},
                {"India"}
        };
    }

    @Test(dataProvider = "countries")
    public void verifyShippingCountry(String country) {
        WebElement deliverIcon = driver.findElement(By.id("glow-ingress-block"));
        deliverIcon.click();
        WebElement listDropdown = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.id("GLUXCountryListDropdown")));
        listDropdown.click();
        WebElement countryLink = driver.findElement(By.xpath(String.format("//a[text()=\"%s\"]", country)));
        countryLink.click();
        WebElement doneButton = driver.findElement(By.xpath("//button[@name=\"glowDoneButton\"]"));
        doneButton.click();
        // need to wait until page automatically reloads
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(deliverIcon));
        WebElement headsetsCategory = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@alt,\"Headsets\")]/../..")));
        headsetsCategory.click();
        WebElement resultItem = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,\"s-result-item\")][2]//a)[1]")));
        resultItem.click();

        WebElement deliveryInfo = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("contextualIngressPtLabel_deliveryShortLine")));

        Assert.assertTrue(deliveryInfo.getText().contains(country));
    }

    @AfterMethod
    public void after() {
        driver.close();
        driver.quit();
    }
}
