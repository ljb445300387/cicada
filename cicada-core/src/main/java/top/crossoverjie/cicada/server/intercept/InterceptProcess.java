package top.crossoverjie.cicada.server.intercept;

import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.reflect.ClassScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/21 18:42
 * @since JDK 1.8
 */
public class InterceptProcess {

    private InterceptProcess() {
    }

    private static InterceptProcess process = new InterceptProcess();

    private static List<BaseCicadaInterceptor> interceptors = new ArrayList<>(10);

    private AppConfig appConfig = AppConfig.getInstance();

    public static InterceptProcess getInstance() {
        return process;
    }

    public void loadInterceptors() throws Exception {
        Map<Class<?>, Integer> map = ClassScanner.getCicadaInterceptor(appConfig.getRootPackageName());
        for (Map.Entry<Class<?>, Integer> classEntry : map.entrySet()) {
            Class<?> interceptorClass = classEntry.getKey();
            BaseCicadaInterceptor interceptor = (BaseCicadaInterceptor) interceptorClass.newInstance();
            interceptor.setOrder(classEntry.getValue());
            interceptors.add(interceptor);
        }
        Collections.sort(interceptors, new OrderComparator());
    }

    public boolean processBefore(Param param) throws Exception {
        for (BaseCicadaInterceptor interceptor : interceptors) {
            if (!interceptor.before(CicadaContext.getContext(), param)) {
                return false;
            }
        }
        return true;
    }

    public void processAfter(Param param) throws Exception {
        for (BaseCicadaInterceptor interceptor : interceptors) {
            interceptor.after(CicadaContext.getContext(), param);
        }
    }
}
