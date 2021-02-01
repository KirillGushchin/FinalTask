package Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    private   Properties properties = new Properties();

    public PropertyUtils(String filePath) {
        try {
            properties.load(new FileReader(filePath));
        } catch (IOException e) {
            System.err.println("Cannot to upload file due to following error: " + e.getMessage());
        }
    }

    public String getStringPropertyValue(String property){
        return properties.getProperty(property);
    }
    public Integer getIntPropertyValue(String property){
        return Integer.parseInt(properties.getProperty(property));
    }

}
