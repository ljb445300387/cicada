package top.crossoverjie.cicada.server.context;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import top.crossoverjie.cicada.server.action.req.CicadaRequest;
import top.crossoverjie.cicada.server.action.res.CicadaResponse;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.constant.CicadaConstant;
import top.crossoverjie.cicada.server.thread.ThreadLocalHolder;

/**
 * Function: Cicada context
 *
 * @author crossoverJie
 * Date: 2018/10/5 00:23
 * @since JDK 1.8
 */
@AllArgsConstructor
public final class CicadaContext {
    private CicadaRequest request;
    private CicadaResponse response;

    public void json(WorkRes workRes) {
        CicadaContext.getResponse().setContentType(CicadaConstant.ContentType.JSON);
        CicadaContext.getResponse().setHttpContent(JSON.toJSONString(workRes));
    }

    public void text(String text) {
        CicadaContext.getResponse().setContentType(CicadaConstant.ContentType.TEXT);
        CicadaContext.getResponse().setHttpContent(text);
    }

    public void html(String html) {
        CicadaContext.getResponse().setContentType(CicadaConstant.ContentType.HTML);
        CicadaContext.getResponse().setHttpContent(html);
    }

    public static CicadaRequest getRequest() {
        return CicadaContext.getContext().request;
    }

    public CicadaRequest request() {
        return CicadaContext.getContext().request;
    }

    public static CicadaResponse getResponse() {
        return CicadaContext.getContext().response;
    }

    public static void setContext(CicadaContext context) {
        ThreadLocalHolder.setCicadaContext(context);
    }

    public static void removeContext() {
        ThreadLocalHolder.removeCicadaContext();
    }

    public static CicadaContext getContext() {
        return ThreadLocalHolder.getCicadaContext();
    }
}
