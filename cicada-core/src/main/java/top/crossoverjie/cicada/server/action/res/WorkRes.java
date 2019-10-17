package top.crossoverjie.cicada.server.action.res;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/8/31 16:03
 * @since JDK 1.8
 */
@Data
@Accessors(chain = true)
public class WorkRes<T> {
    private String code;
    private String message;
    private T dataBody;
}
