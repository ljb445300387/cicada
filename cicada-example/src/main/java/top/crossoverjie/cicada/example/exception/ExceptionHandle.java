package top.crossoverjie.cicada.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.annotation.CicadaBean;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.exception.GlobalHandelException;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-07-11 12:07
 * @since JDK 1.8
 */

@CicadaBean
@Slf4j
public class ExceptionHandle implements GlobalHandelException {
    @Override
    public void resolveException(CicadaContext context, Exception e) {
        log.error("Exception", e);
        context.json(new WorkRes().setCode("500").setMessage(e.getClass().getName() + "系统运行出现异常"));
    }
}
