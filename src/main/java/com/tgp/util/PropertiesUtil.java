package com.tgp.util;

import com.tgp.view.alert.AlertFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties;

    public static String getValue(String key) {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("app.properties"));
            } catch (IOException e) {
                AlertFactory.showErrorAlert(e);
            }
        }
        return properties.get(key).toString();
    }
}
