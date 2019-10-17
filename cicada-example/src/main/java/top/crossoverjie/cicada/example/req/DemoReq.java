package top.crossoverjie.cicada.example.req;

import lombok.Data;
import lombok.ToString;
import top.crossoverjie.cicada.server.action.req.WorkReq;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/8/31 18:59
 * @since JDK 1.8
 */
@Data
@ToString(callSuper = true)
public class DemoReq extends WorkReq {
    private Integer id;
    private String name;
}
