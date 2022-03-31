package org.course.properties.converters;

import org.course.enumeration.SupportedBrowsers;

import static org.course.enumeration.SupportedBrowsers.LOCAL_CHROME;
import static org.course.enumeration.SupportedBrowsers.LOCAL_FIREFOX;

public class SupportedBrowserConverter {

    public static SupportedBrowsers valueOfWebBrowser(String webBrowserName) {
        switch(webBrowserName) {
            case "local_chrome":
                return LOCAL_CHROME;
            case "local_firefox":
                return LOCAL_FIREFOX;
            default:
                throw new IllegalArgumentException();
        }
    }
}
