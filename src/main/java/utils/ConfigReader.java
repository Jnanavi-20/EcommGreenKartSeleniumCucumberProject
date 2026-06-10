package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value;
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }
	
 
}
