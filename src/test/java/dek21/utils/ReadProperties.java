package dek21.utils;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class ReadProperties {

    private static ReadProperties instance = null;
    private final Properties properties;

    @SneakyThrows
    private ReadProperties() {
        properties = new Properties();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
    }

    public static synchronized ReadProperties getInstance() {
        if (instance == null) {
            instance = new ReadProperties();
        }
        return instance;
    }

    public String getValue(String key) {
        return this.properties.getProperty(key, format("No key %s found", key));
    }
}