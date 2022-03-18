package org.course.pageobjects.delivertopages;

import org.course.pageobjects.delivertopages.modules.DeliveryModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends BasePage {

    @FindBy(xpath = "//a[@aria-label=\"Headsets\"]")
    private WebElement headsetesLink;

    //    @FindBy(id = "glow-ingress-block")
    private WebElement deliverIcon;

    @FindBy(xpath = "//span[text()=\"Apply\"]/..")
    private WebElement applyZipCodeButton;

    @FindBy(id = "glow-ingress-line2")
    private WebElement destinationName;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public SearchPage headsetsLinkClick() {
        headsetesLink.click();
        return new SearchPage(driver);
    }

    public void changeZipCode(String zipCode) {
        deliverIcon.click();
        DeliveryModule deliveryModule = new DeliveryModule(driver);
        deliveryModule.setZipCode(zipCode);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(deliverIcon));
        destinationName = driver.findElement(By.id("glow-ingress-line2"));
    }

    public MainPage changeCountry(String country) {
        deliverIcon.click();
        DeliveryModule deliveryModule = new DeliveryModule(driver);
        deliveryModule.setCountry(country);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(deliverIcon));
        return this;
    }

    public List<String> getAvailableCountires() {
        deliverIcon.click();
        DeliveryModule deliveryModule = new DeliveryModule(driver);
        return deliveryModule.getCountiresList().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getDestinationName() {
        return destinationName.getText();
    }

    public MainPage open() {
        driver.get("https://amazon.com/");
//        need to initialize deliverIcon here because after using @FindBy stalenessOf doesn't work
        deliverIcon = driver.findElement(By.id("glow-ingress-block"));
        return this;
    }
}

