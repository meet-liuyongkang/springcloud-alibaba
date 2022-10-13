package con.jiangyue.gateway.config;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author liuyongkang
 * @date Create in 2022/10/12 15:10
 *
 * 自定义过滤器
 * 实现是否打印日志
 *
 * 注意：过滤器名称格式为 XxxGatewayFilterFactory , 配置文件中的参数名即为 Xxx
 */
@Component
public class LoggerGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggerGatewayFilterFactory.Config> {

    /**
     * 构造方法必须要，否则默认的构造方法不会传Config.class
     */
    public LoggerGatewayFilterFactory() {
        super(LoggerGatewayFilterFactory.Config.class);
    }

    /**
     * 将配置文件中的数据，封装到下面的Config配置类中
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog", "cacheLog");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if(config.consoleLog){
                    System.out.println("consoleLog开启了。。。");
                }
                if(config.cacheLog){
                    System.out.println("cacheLog也开启了。。。");
                }
                return chain.filter(exchange);
            }
        };
    }

    @Data
    public static class Config{
        /**
         * 是否打印 控制台日志
         */
        private Boolean consoleLog;

        /**
         * 是否打印 缓存日志
         */
        private Boolean cacheLog;
    }
}
