package top.crossoverjie.cicada.server.init;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import top.crossoverjie.cicada.server.handle.HttpDispatcherHandler;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 17/05/2018 18:51
 * @since JDK 1.8
 */
public class CicadaInitializer extends ChannelInitializer<Channel> {
    private final HttpDispatcherHandler httpDispatcherHandler = new HttpDispatcherHandler() ;

    @Override
    public void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new HttpRequestDecoder())
                .addLast(new HttpResponseEncoder())
                .addLast(new ChunkedWriteHandler())
                .addLast(httpDispatcherHandler)
                .addLast("logging", new LoggingHandler(LogLevel.INFO));
    }
}
