package top.crossoverjie.cicada.server.thread;

import io.netty.util.concurrent.FastThreadLocal;
import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/9/10 19:50
 * @since JDK 1.8
 */
public class ThreadLocalHolder {

    private static final FastThreadLocal<Long> LOCAL_TIME = new FastThreadLocal();

    private static final FastThreadLocal<CicadaContext> CICADA_CONTEXT = new FastThreadLocal();

    public static void setCicadaContext(CicadaContext context) {
        CICADA_CONTEXT.set(context);
    }

    public static void removeCicadaContext() {
        CICADA_CONTEXT.remove();
    }

    public static CicadaContext getCicadaContext() {
        return CICADA_CONTEXT.get();
    }

    public static void setLocalTime(long time) {
        LOCAL_TIME.set(time);
    }

    public static Long getLocalTime() {
        Long time = LOCAL_TIME.get();
        LOCAL_TIME.remove();
        return time;
    }

}
