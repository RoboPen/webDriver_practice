package org.course.pageobjects.searchfieldpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndexPage extends BasePage {
    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchBtn;

    public IndexPage(WebDriver webDriver) {
        super(webDriver);
    }

    public IndexPage open() {
        driver.get("https://amazon.com");
        return this;
    }

    public SearchResultPage searchProduct(String productName) {
        searchBox.sendKeys(productName);
        searchBtn.click();
        return new SearchResultPage(driver, productName);
    }
}
