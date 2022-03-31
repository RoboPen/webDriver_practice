package uitests.filtertests;


import org.course.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver webDriver;

    protected void setupDriver() {
        webDriver = new WebDriverFactory().getWebDriver();
        webDriver.manage().window().maximize();
    }

    protected void stop() {
        webDriver.quit();
    }
}
