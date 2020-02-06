import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtils {
    private Properties props = null;
    private static volatile PropertiesUtils conf;

    private PropertiesUtils() {
        props = new Properties();
        loadConfigProps();
    }

    public static PropertiesUtils getInstance() {
        if (conf == null) {
            synchronized (PropertiesUtils.class) {
                if (conf == null) {
                    conf = new PropertiesUtils();
                }
            }
        }
        return conf;
    }

    private void loadConfigProps() {
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(
                    "configer.properties");
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            props.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getProperty(String key) {
        String tmp = props.getProperty(key);
        if (StringUtils.isNotEmpty(tmp)) {
            return tmp.trim();
        }
        return tmp;
    }
}
