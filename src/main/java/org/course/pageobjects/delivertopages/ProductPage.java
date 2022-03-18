package org.course.pageobjects.delivertopages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//div[@id=\"contextualIngressPtLabel_deliveryShortLine\"]/span[2]")
    private WebElement countryName;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getCountryName() {
        return countryName.getText();
    }
}
