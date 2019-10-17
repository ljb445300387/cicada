package top.crossoverjie.cicada.server.configuration;

import lombok.Data;
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
@Data
public abstract class AbstractCicadaConfiguration {
    private String propertiesName;

    private Properties properties;

    public String get(String key) {
        return properties.get(key) == null ? null : properties.get(key).toString();
    }
}
