package top.crossoverjie.cicada.server.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.server.bean.CicadaBeanManager;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.configuration.ApplicationConfiguration;
import top.crossoverjie.cicada.server.constant.CicadaConstant;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.init.CicadaInitializer;
import top.crossoverjie.cicada.server.thread.ThreadLocalHolder;

import static top.crossoverjie.cicada.server.configuration.ConfigurationHolder.getConfiguration;
import static top.crossoverjie.cicada.server.constant.CicadaConstant.SystemProperties.APPLICATION_THREAD_SHUTDOWN_NAME;
import static top.crossoverjie.cicada.server.constant.CicadaConstant.SystemProperties.APPLICATION_THREAD_WORK_NAME;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/9/10 21:56
 * @since JDK 1.8
 */
@Slf4j
public class NettyBootStrap {
    private static AppConfig appConfig = AppConfig.getInstance();
    private static EventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
    private static EventLoopGroup work = new NioEventLoopGroup(0, new DefaultThreadFactory(APPLICATION_THREAD_WORK_NAME));
    private static Channel channel;

    public static void startCicada() throws Exception {
        startServer();
        shutDownServer();
        joinServer();
    }

    private static void startServer() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new CicadaInitializer());

        ChannelFuture future = bootstrap.bind(AppConfig.getInstance().getPort()).sync();
        if (future.isSuccess()) {
            appLog();
        }
        channel = future.channel();
    }

    private static void joinServer() throws Exception {
        channel.closeFuture().sync();
    }

    private static void appLog() {
        Long start = ThreadLocalHolder.getLocalTime();
        ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) getConfiguration(ApplicationConfiguration.class);
        long end = System.currentTimeMillis();
        log.info("Cicada started on port: {}.cost {}ms", applicationConfiguration.get(CicadaConstant.CICADA_PORT), end - start);
        log.info(">> access http://{}:{}{} <<", "127.0.0.1", appConfig.getPort(), appConfig.getRootPath());
    }

    /**
     * shutdown server
     */
    private static void shutDownServer() {
        Thread shutDownThread = new Thread(() -> {
            log.info("Cicada server stop...");
            CicadaContext.removeContext();

            CicadaBeanManager.getInstance().releaseBean();

            boss.shutdownGracefully();
            work.shutdownGracefully();

            log.info("Cicada server has been successfully stopped.");
        });
        shutDownThread.setName(APPLICATION_THREAD_SHUTDOWN_NAME);
        Runtime.getRuntime().addShutdownHook(shutDownThread);
    }

}
