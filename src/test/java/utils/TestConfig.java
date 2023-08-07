package utils;

import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private static String PROPERTIES_FILE;
    private static String environment = System.getProperty("env");
    private static Properties envprops;


    // To load the test properties (uri's & keys & connection strings )
    public static void loadTestProperties() {
        envprops = new Properties();
        try {
            if (getEnvironment() == null) {
                PROPERTIES_FILE = System.getProperty("user.dir")
                        + "/src/test/resources/test.properties";
            } else {
                PROPERTIES_FILE = System.getProperty("user.dir") + "/src/test/resources/"
                        + getEnvironment() + ".properties";
            }
            InputStream input = new FileInputStream(PROPERTIES_FILE);
            envprops.load(input);
        } catch (IOException e) {
            Assert.fail("Error while executing loadTestProperties");
        }
    }


    // To get the value of specific property
    public static String getEnvProperty(final String key) {
        if (envprops == null) {
            loadTestProperties();
        }
        return envprops.getProperty(key);
    }



    public static String getEnvironment() {
        return environment;
    }
}
