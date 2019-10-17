package top.crossoverjie.cicada.example.intercept;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.annotation.Interceptor;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.intercept.BaseCicadaInterceptor;

/**
 * Function: common interceptor
 *
 * @author crossoverJie
 *         Date: 2018/9/2 14:39
 * @since JDK 1.8
 */
@Slf4j
@Interceptor(order = 1)
public class LoggerInterceptor extends BaseCicadaInterceptor {
    @Override
    public boolean before(CicadaContext context, Param param) throws Exception {
        return super.before(context, param);
    }

    @Override
    public void after(CicadaContext context, Param param) {
        log.info("logger param=[{}]",param.toString());
    }
}
