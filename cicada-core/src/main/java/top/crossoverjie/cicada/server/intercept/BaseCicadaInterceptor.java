package top.crossoverjie.cicada.server.intercept;

import lombok.Getter;
import lombok.Setter;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function: common interceptor
 *
 * @author crossoverJie
 * Date: 2018/9/2 14:39
 * @since JDK 1.8
 */
public abstract class BaseCicadaInterceptor {

    @Setter
    @Getter
    private int order;

    /**
     * before
     *
     * @param context
     * @param param
     * @return true if the execution chain should proceed with the next interceptor or the handler itself
     * @throws Exception
     */
    protected boolean before(CicadaContext context, Param param) throws Exception {
        return true;
    }


    /**
     * after
     *
     * @param context
     * @param param
     */
    protected void after(CicadaContext context, Param param) {
    }
}
