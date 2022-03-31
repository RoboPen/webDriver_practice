package org.course.filterpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ComputersPage extends BasePage {

//    @FindBy(xpath = "//span[text() = \"TAKAGI\"]")
//    private WebElement searchButton;

    public ComputersPage(WebDriver driver) {
        super(driver);
    }

    public ComputersPage chooseFeaturedBrand(String name) {
        WebElement brandButton = webDriver.findElement(By.xpath("//span[text() = \"" + name + "\"]"));
        brandButton.click();
        return this;
    }

    public ComputersPage choosePriceRangeUnder25() {
        WebElement searchButton = webDriver.findElement(By.xpath("//span[text() = \"Under $25\"]"));
        searchButton.click();
        return this;
    }

    public ComputersPage chooseLowestToHighestSorting() {
        WebElement searchDropdownList = webDriver.findElement(By.id("a-autoid-0-announce"));
        searchDropdownList.click();
        WebElement selectDropdownList = webDriver.findElement(By.id("s-result-sort-select_1"));
        selectDropdownList.click();
        return this;
    }

    public List<String> getPriceFilteringResults() {
        return webDriver.findElements(By.xpath("//span[@class=\"a-price\"]/span[@class=\"a-offscreen\"]"))
                .stream()
                .map(webElement -> webElement.getAttribute("textContent").replace("$", ""))
                .collect(Collectors.toList());
    }

    public List<String> getBrandFilteringResults() {
        return webDriver.findElements(By.xpath("//span[@data-component-type=\"s-search-results\"]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Float> getPriceSortingResults() {
        return webDriver.findElements(By.className("a-price-whole"))
                .stream()
                .map(webElement -> Float.parseFloat(webElement.getText()))
                .collect(Collectors.toList());
    }
}
