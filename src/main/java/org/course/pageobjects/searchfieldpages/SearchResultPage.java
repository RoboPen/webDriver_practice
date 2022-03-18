package org.course.pageobjects.searchfieldpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class SearchResultPage extends BasePage {
    private String searchBoxInputValue;

    public SearchResultPage(WebDriver webDriver, String productName) {
        super(webDriver);
        this.searchBoxInputValue = productName;
    }

    @FindBy(xpath = "//span[@data-component-type='s-search-results']//span")
    private WebElement noResultMsg;

    @FindBy(xpath = "(//span[@data-component-type='s-search-results']//span)[2]")
    private WebElement incorrectProductName;

    @FindBy(xpath = "//span[@class='a-color-state a-text-bold']")
    private WebElement productName;

    @FindBy(xpath = "//span[@class='s-pagination-strip']/span[4]")
    @CacheLookup
    private List<WebElement> pageNum;

    @FindBy(xpath = "//*[contains(@class,'s-pagination-next')]")
    private WebElement nextBtn;

    @FindBy(xpath = "//div[contains(@class,'s-card-container')]//h2")
    private List<WebElement> productTitles;

    protected SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public String getTextForIncorrectProductName() {
        return noResultMsg.getText() + " " + incorrectProductName.getText();
    }

    public String getProductNameFromResultInfoBar() {
        return productName.getText().replace("\"", "");
    }

    public boolean checkAnyTitleContainsProductName() {
        boolean anyTitleContainsInputWord = false;
        int index = 1;
        int numOfPage = getResultNumPage();

        while (index <= numOfPage) {
            //checking if any title of the page contains input
            anyTitleContainsInputWord = productTitles
                    .stream()
                    .map(e -> e.getText().toLowerCase())
                    .anyMatch(e -> e.contains(searchBoxInputValue.toLowerCase()));

            //if any title contains input, break the loop
            if (anyTitleContainsInputWord) {
                break;
            }

            //click next button until the last page
            if (index != numOfPage) {
                waitForElementVisibility(nextBtn);
                nextBtn.click();
            }
            index++;
        }

        return anyTitleContainsInputWord;
    }

    //assign 1 if there is only one page, otherwise get num of pages from pagination strip
    //using List to avoid NoSuchElementException when there is only one page
    //therefore pageNum webelement doesnt exist
    private int getResultNumPage() {
        return pageNum.isEmpty() ? 1 : Integer.parseInt(pageNum.get(0).getText());
    }

    private void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }


}
