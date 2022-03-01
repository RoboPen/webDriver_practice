package org.epam.poland.course.webDriver_practice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;

public class SearchFieldTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @DataProvider(name = "wrongData")
    public Object[][] wrongData() {
        return new Object[][]{
                {"qwrt4Q&577&&$$$fdsbdrewrewrewrew"}
        };
    }

    @Test(dataProvider = "wrongData")
    public void verifyNoResultForIncorrectInput(String noResultsInput) {
        driver.get("https://www.amazon.com/");

        WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
        searchInput.sendKeys(noResultsInput);

        WebElement confirmSearchBtn = driver.findElement(By.id("nav-search-submit-button"));
        confirmSearchBtn.click();

        String noResultsSpanText = driver.findElement(By.xpath("//span[@data-component-type='s-search-results']//span")).getText();
        String incorrectDataSpanText = driver.findElement(By.xpath("(//span[@data-component-type='s-search-results']//span)[2]")).getText();

        Assert.assertEquals(noResultsSpanText + " " + noResultsInput, "No results for " + incorrectDataSpanText);
    }

    @DataProvider(name = "dataInput")
    public Object[][] dataInput() {
        return new Object[][]{
                {"laptop"}
        };
    }

    @Test(dataProvider = "dataInput")
    public void verifyInputIsPresentNextToResultsNumber(String dataInput) {
        driver.get("https://www.amazon.com/");

        WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
        searchInput.sendKeys(dataInput);

        WebElement confirmSearchBtn = driver.findElement(By.id("nav-search-submit-button"));
        confirmSearchBtn.click();

        String searchedWordText = driver.findElement(By.xpath("//span[@data-component-type='s-result-info-bar']//span[@class='a-color-state a-text-bold']"))
                .getText();

        Assert.assertTrue(searchedWordText.contains(dataInput));
    }

    @Test(dataProvider = "dataInput")
    public void verifyAnyResultsTitleContainsInput(String dataInput) {
        driver.get("https://www.amazon.com/");

        WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
        searchInput.sendKeys(dataInput);

        WebElement confirmSearchBtn = driver.findElement(By.id("nav-search-submit-button"));
        confirmSearchBtn.click();

        //if next button is not present (there is only one page), assign 1
        int numOfPages = driver.findElements(By.xpath("//span[@class='s-pagination-strip']/span[4]")).isEmpty() ?
                 1 : Integer.parseInt(driver.findElement(By.xpath("//span[@class='s-pagination-strip']/span[4]")).getText());

        boolean anyTitleContainsInputWord = false;

        for (int i = 1; i <= numOfPages; i++) {
            //checking if any title of the page contains input
            anyTitleContainsInputWord = driver.findElements(By.xpath("//div[contains(@class,'s-card-container')]//span[contains(@class,'a-size-medium')]"))
                    .stream()
                    .map(WebElement::getText)
                    .map(String::toLowerCase)
                    .anyMatch(e -> e.contains(dataInput.toLowerCase()));

            //if any title contains input, break the loop
            if(anyTitleContainsInputWord){
                break;
            }

            //click next button until its not clickable, based on num of pages
            if (numOfPages != i) {
                WebElement nextButton = new WebDriverWait(driver, Duration.ofSeconds(6))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'s-pagination-next')]")));
                nextButton.click();
            }
        }

        Assert.assertTrue(anyTitleContainsInputWord);
    }

    @AfterTest
    public void close() {
        driver.quit();
    }
}
