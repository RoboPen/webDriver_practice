package org.course.properties.holder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyHolder {
    private final Properties property = new Properties();

    {
        try (FileInputStream fis = new FileInputStream("src/test/resources/browser.properties")) {
            property.load(fis);

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String readProperty(String propertyName) {
        return property.getProperty(propertyName);
    }
}
