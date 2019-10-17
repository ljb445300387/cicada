package top.crossoverjie.cicada.server.config;

import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.Getter;
import lombok.Setter;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.util.PathUtil;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/9/1 14:00
 * @since JDK 1.8
 */
public final class AppConfig {


    private AppConfig() {
    }

    private static AppConfig config = new AppConfig();

    @Getter
    private String rootPackageName;

    @Getter
    @Setter
    private String rootPath;

    @Getter
    @Setter
    private Integer port = 7317;

    public static AppConfig getInstance() {
        return config;
    }

    public void setRootPackageName(Class<?> clazz) {
        if (clazz.getPackage() == null) {
            throw new CicadaException(StatusEnum.NULL_PACKAGE,
                    "[" + clazz.getName() + ".java]:" + StatusEnum.NULL_PACKAGE.getMessage());
        }
        this.rootPackageName = clazz.getPackage().getName();
    }

    /**
     * check Root Path
     *
     * @param uri
     * @param queryStringDecoder
     * @return
     */
    public void checkRootPath(String uri, QueryStringDecoder queryStringDecoder) {
        if (!PathUtil.getRootPath(queryStringDecoder.path()).equals(this.getRootPath())) {
            throw new CicadaException(StatusEnum.REQUEST_ERROR, uri);
        }
    }
}
