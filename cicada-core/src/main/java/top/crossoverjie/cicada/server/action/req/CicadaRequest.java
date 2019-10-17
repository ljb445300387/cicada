package top.crossoverjie.cicada.server.action.req;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/5 00:40
 * @since JDK 1.8
 */
public interface CicadaRequest {

    String getMethod();

    String getUrl();

    Cookie getCookie(String key);

}
