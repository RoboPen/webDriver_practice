package org.epam.course.properties.converters;

import org.course.invoker.WebDriverInvoker;
import org.course.invoker.implementations.ChromeBrowser;
import org.course.invoker.implementations.FirefoxBrowser;

public class BrowserConverter {

    public static WebDriverInvoker valueOfWebBrowser(String webBrowserName) {
        switch(webBrowserName) {
            case "local_chrome":
                return ChromeBrowser.getChromeBrowserInstance();
            case "local_firefox":
                return FirefoxBrowser.getFirefoxBrowserInstance();
            default:
                throw new IllegalArgumentException();
        }
    }
}
