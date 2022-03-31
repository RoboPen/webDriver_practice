package org.course.filterpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@aria-label=\"Computers & Accessories\"]")
    private WebElement computersCategory;

    public ComputersPage computersCategoryClick() {
        computersCategory.click();
        return new ComputersPage(webDriver);
    }

    public MainPage open() {
        webDriver.get("https://amazon.com/");
        return this;
    }
}
