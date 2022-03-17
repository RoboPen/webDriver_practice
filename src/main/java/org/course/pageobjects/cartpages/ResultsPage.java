package org.course.pageobjects.cartpages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class ResultsPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, \"a-section aok-relative\")]/img")
    private List<WebElement> items;

    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;

    private WebElement selectedItem;

    public ResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public ItemPage drawAnItem() {
        while (true) {
            selectItemByIndex().click();
            try {
                addToCartButton.isDisplayed();
                return new ItemPage(webDriver);
            } catch (NoSuchElementException e) {
                webDriver.get("https://www.amazon.com/b?node=16225015011&pf_rd_r=QN0PGK29JQ4VWNP5GJYP&pf_rd_p=e5b0c85f-569c-4c90-a58f-0c0a260e45a0&pd_rd_r=c7cb020a-72d6-45f9-a523-6e430aafa019&pd_rd_w=BaH4A&pd_rd_wg=wMcAj&ref_=pd_gw_unk");
                System.err.println("Item can be only pre-ordered");
            }
        }
    }

    private WebElement selectItemByIndex() {
        Random random = new Random();
        int index = random.nextInt(items.size());
        selectedItem = items.get(index);
        return selectedItem;
    }
}
