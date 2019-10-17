package top.crossoverjie.cicada.server.reflect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ScannerTest {

    public static final String NAME = "top.crossoverjie.cicada.server";
    public static final String NAME1 = "top.crossoverjie.cicada.server";
    public static final String NAME2 = "top.crossoverjie.cicada.server";

    @Test
    public void getClasses() throws Exception {
        Set<Class<?>> classes = ClassScanner.getClasses(NAME);
        log.info("classes=[{}]", JSON.toJSONString(classes));
    }


    @Test
    public void getActionAction() throws Exception {
        Map<String, Class<?>> cicadaAction = ClassScanner.getCicadaBean(NAME1);
        log.info("classes=[{}]", JSON.toJSONString(cicadaAction));
    }


    @Test
    public void getConfiguration() throws Exception {
        List<Class<?>> configuration = ClassScanner.getConfiguration(NAME2);
        log.info("configuration=[{}]", configuration.toString());
    }


    @Test
    public void stringTest() {
        String text = "/cicada-example/routeAction/getUser";
        text = text.replace("/cicada-example", "");
        log.info(text);
    }


}