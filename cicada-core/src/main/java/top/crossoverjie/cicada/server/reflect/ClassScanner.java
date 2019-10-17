package top.crossoverjie.cicada.server.reflect;

import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.base.bean.CicadaBeanFactory;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaBean;
import top.crossoverjie.cicada.server.annotation.Interceptor;
import top.crossoverjie.cicada.server.bean.CicadaDefaultBean;
import top.crossoverjie.cicada.server.configuration.AbstractCicadaConfiguration;
import top.crossoverjie.cicada.server.configuration.ApplicationConfiguration;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Function: package Scanner
 *
 * @author crossoverJie
 * Date: 2018/9/1 11:36
 * @since JDK 1.8
 */
@Slf4j
public final class ClassScanner {
    private static Map<String, Class<?>> actionMap = null;
    private static Map<Class<?>, Integer> interceptorMap = null;
    private static Set<Class<?>> classes = null;
    private static Set<Class<?>> cicada_classes = null;
    private static List<Class<?>> configurationList = null;

    /**
     * get Configuration
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getConfiguration(String packageName) {

        if (configurationList != null) {
            return configurationList;
        }
        Set<Class<?>> classSet = getClasses(packageName);
        // Manually add ApplicationConfiguration
        classSet.add(ApplicationConfiguration.class);

        if (classSet == null || classSet.isEmpty()) {
            return configurationList;
        }

        configurationList = new ArrayList<>(16);
        for (Class<?> cls : classSet) {
            if (cls.getSuperclass() != AbstractCicadaConfiguration.class) {
                continue;
            }
            configurationList.add(cls);
        }
        return configurationList;
    }

    /**
     * get @CicadaAction & @CicadaBean
     *
     * @param packageName
     * @return
     */
    public static Map<String, Class<?>> getCicadaBean(String packageName) {

        if (actionMap != null) {
            return actionMap;
        }
        Set<Class<?>> clsList = getClasses(packageName);
        if (clsList == null || clsList.isEmpty()) {
            return actionMap;
        }
        actionMap = new HashMap<>(16);
        for (Class<?> cls : clsList) {
            CicadaAction action = cls.getAnnotation(CicadaAction.class);
            CicadaBean bean = cls.getAnnotation(CicadaBean.class);
            if (action == null && bean == null) {
                continue;
            }

            if (action != null) {
                actionMap.put(action.value() == null ? cls.getName() : action.value(), cls);
            }

            if (bean != null) {
                actionMap.put(bean.value() == null ? cls.getName() : bean.value(), cls);
            }
        }
        return actionMap;
    }

    /**
     * whether is the target class
     *
     * @param clazz
     * @param target
     * @return
     */
    public static boolean isInterface(Class<?> clazz, Class<?> target) {
        for (Class<?> aClass : clazz.getInterfaces()) {
            if (aClass.getName().equals(target.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * get @Interceptor
     *
     * @param packageName
     * @return
     * @throws Exception
     */
    public static Map<Class<?>, Integer> getCicadaInterceptor(String packageName) throws Exception {
        if (interceptorMap != null) {
            return interceptorMap;
        }
        Set<Class<?>> clsList = getClasses(packageName);
        if (clsList == null || clsList.isEmpty()) {
            return interceptorMap;
        }
        interceptorMap = new HashMap<>(16);
        for (Class<?> cls : clsList) {
            Annotation annotation = cls.getAnnotation(Interceptor.class);
            if (annotation == null) {
                continue;
            }
            Interceptor interceptor = (Interceptor) annotation;
            interceptorMap.put(cls, interceptor.order());
        }
        return interceptorMap;
    }

    /**
     * get All classes
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClasses(String packageName) {
        if (classes == null) {
            classes = new HashSet<>(32);
            baseScanner(packageName, classes);
        }
        return classes;
    }

    private static void baseScanner(String packageName, Set set) {
        try {
            String packageDirName = packageName.replace('.', '/');
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, true, set);
                    continue;
                }
                if ("jar".equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = getName(entry);
                        if (!name.startsWith(packageDirName)) {
                            continue;
                        }
                        int idx = name.lastIndexOf('/');
                        if (idx != -1) {
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            set.add(Class.forName(packageName + '.' + className));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("IOException", e);
        }
    }

    private static String getName(JarEntry entry) {
        String name = entry.getName();
        if (name.charAt(0) == '/') {
            name = name.substring(1);
        }
        return name;
    }

    private static void findAndAddClassesInPackageByFile(String packageName,
                                                         String packagePath,
                                                         final boolean recursive,
                                                         Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        for (File file : files) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
                continue;
            }
            try {
                String className = file.getName().substring(0, file.getName().length() - 6);
                classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
            } catch (ClassNotFoundException e) {
                log.error("ClassNotFoundException", e);
            }
        }
    }


    private static final String BASE_PACKAGE = "top.crossoverjie.cicada";

    /**
     * get custom route bean
     *
     * @return
     */
    public static Class<?> getBeanFactory() {
        List<Class<?>> classList = new ArrayList<>();
        Set<Class<?>> classes = getCustomRouteBeanClasses(BASE_PACKAGE);
        for (Class<?> aClass : classes) {
            if (aClass.getInterfaces().length == 0) {
                continue;
            }
            if (aClass.getInterfaces()[0] != CicadaBeanFactory.class) {
                continue;
            }
            classList.add(aClass);
        }

        if (classList.size() > 2) {
            throw new CicadaException(StatusEnum.DUPLICATE_IOC);
        }

        if (classList.size() == 2) {
            Iterator<Class<?>> iterator = classList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() == CicadaDefaultBean.class) {
                    iterator.remove();
                }
            }
        }

        return classList.get(0);
    }


    public static Set<Class<?>> getCustomRouteBeanClasses(String packageName) {
        if (cicada_classes == null) {
            cicada_classes = new HashSet<>(32);
            baseScanner(packageName, cicada_classes);
        }
        return cicada_classes;
    }
}
