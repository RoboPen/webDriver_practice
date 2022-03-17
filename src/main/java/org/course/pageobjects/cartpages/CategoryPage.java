package org.course.pageobjects.cartpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CategoryPage extends BasePage {

    @FindBy(xpath = "//a[@aria-label = \"Toys & Games\"]")
    private WebElement category;

    public CategoryPage(WebDriver webDriver) {
        super(webDriver);
    }

    public CategoryPage open() {
        webDriver.get("https://www.amazon.com/");
        return this;
    }

    public ResultsPage searchCategory() {
        category.click();
        return new ResultsPage(webDriver);
    }
}
