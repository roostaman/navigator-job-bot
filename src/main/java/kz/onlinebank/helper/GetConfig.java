package kz.onlinebank.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GetConfig {

    private static final Properties properties = new Properties();

    static  {
        try (InputStream inputStream = Files.newInputStream(Path.of("src/main/resources/env.properties"))) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties file: " + "src/main/resources/env.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
