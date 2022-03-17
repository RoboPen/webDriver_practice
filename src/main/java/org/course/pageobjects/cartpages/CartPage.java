package org.course.pageobjects.cartpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class, \"sw-atc-message-section\")]//span[normalize-space(text()=\"Added to Cart\")]")
    private WebElement addedToCartConfirmation;

    @FindBy(id = "nav-cart-count")
    private WebElement numberOfItemsInTheCart;

    @FindBy(id = "nav-cart")
    private WebElement cartIcon;

    @FindBy(xpath = "//input[@value =\"Delete\"]")
    private WebElement deleteButton;

    @FindBy(xpath = "//span[text()=\"$0.00\"]")
    private WebElement priceOfItemInTheCart;

    private WebElement cartIsEmptyConfirmation;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    public int getNumberOfItemsInTheCart() {
        return Integer.parseInt(numberOfItemsInTheCart.getText());
    }

    public String getAddedToCartConfirmation() {
        return addedToCartConfirmation.getText();
    }

    public void deleteItemFromTheCart() {
        cartIcon.click();
        deleteButton.click();
    }

    public String getCartIsEmptyConfirmation() {
        //noinspection deprecation
        cartIsEmptyConfirmation = new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"a-row sc-cart-header\"]//h1")));
        return cartIsEmptyConfirmation.getText();
    }

    public String getPriceOfItemInTheCart() {
        return priceOfItemInTheCart.getText();
    }
}

