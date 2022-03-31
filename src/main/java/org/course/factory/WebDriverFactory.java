package org.course.factory;

import org.course.enumeration.SupportedBrowsers;
import org.course.properties.converters.SupportedBrowserConverter;
import org.course.properties.holder.PropertyHolder;
import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
    public WebDriver getWebDriver() {
        String propertyValue = new PropertyHolder().readProperty("browser");
        SupportedBrowsers browser = SupportedBrowserConverter.valueOfWebBrowser(propertyValue);

        return browser.getWebDriver();
    }
}
