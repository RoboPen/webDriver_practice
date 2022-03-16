package uitests.searchfieldtest;

import org.course.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    protected WebDriver driver = new WebDriverFactory().getWebDriver();

    protected void setUpDriver() {
        driver.manage().window().maximize();
    }

    protected void quit() {
        driver.quit();
    }
}
