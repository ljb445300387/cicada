package top.crossoverjie.cicada.example.intercept;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.annotation.Interceptor;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.intercept.BaseCicadaInterceptor;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/9/2 15:21
 * @since JDK 1.8
 */
@Slf4j
@Interceptor(order = 1)
public class ExecuteTimeInterceptor extends BaseCicadaInterceptor {
    private Long start;
    private Long end;

    @Override
    public boolean before(CicadaContext context, Param param) {
        start = System.currentTimeMillis();
        log.info("拦截请求");
        return true;
    }

    @Override
    public void after(CicadaContext context, Param param) {
        end = System.currentTimeMillis();
        log.info("cast [{}] times", end - start);
    }
}
