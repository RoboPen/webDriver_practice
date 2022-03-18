package uitestes.delivertotest;

import factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;

    protected void setUpDriver() {
        driver = new WebDriverFactory().getWebDriver();
        driver.manage().window().maximize();
    }

    protected void quit() {
        driver.quit();
    }
}
