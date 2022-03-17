package org.course.pageobjects.cartpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemPage extends BasePage {

    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;

    public ItemPage(WebDriver webDriver) {
        super(webDriver);
    }

    public CartPage addToTheCart() {
        addToCartButton.submit();
        return new CartPage(webDriver);
    }
}
