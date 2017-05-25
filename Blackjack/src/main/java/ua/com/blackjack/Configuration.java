package ua.com.blackjack;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private Properties properties;

    public Configuration() {
        properties = new Properties();
        try (InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("server.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getServerName(){
        return properties.getProperty("server.name");
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }
}
