package org.course.pageobjects.delivertopages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(xpath = "(//div[contains(@class,'s-result-item') and not(contains(@class,\"AdHolder\")) and not(contains(@class,\"s-widget-spacing-large\"))]//a)[1]")
    List<WebElement> searchResults;

    public ProductPage clickFirstResult() {
        searchResults.get(0).click();
        return new ProductPage(driver);
    }

    protected SearchPage(WebDriver driver) {
        super(driver);
    }
}
