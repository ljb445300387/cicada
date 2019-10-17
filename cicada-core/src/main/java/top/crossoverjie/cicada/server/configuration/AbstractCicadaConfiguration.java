package top.crossoverjie.cicada.server.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/9/8 16:28
 * @since JDK 1.8
 */
public abstract class AbstractCicadaConfiguration {
    /**
     * file name
     */
    @Setter
    @Getter
    private String propertiesName;

    @Setter
    private Properties properties;

    public String get(String key) {
        return properties.get(key) == null ? null : properties.get(key).toString();
    }

    @Override
    public String toString() {
        return "AbstractCicadaConfiguration{" +
                "propertiesName='" + propertiesName + '\'' +
                ", properties=" + properties +
                '}';
    }
}
