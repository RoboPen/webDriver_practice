package org.course.filterpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver webDriver;

    public BasePage(WebDriver driver) {
        webDriver = driver;
        PageFactory.initElements(driver, this);
    }
}
