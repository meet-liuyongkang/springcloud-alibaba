package con.jiangyue.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liuyongkang
 * @date Create in 2022/10/13 10:39
 */
@Configuration
public class GatewayConfig {

    private List<ViewResolver> viewResolverList;

    private ServerCodecConfigurer serverCodecConfigurer;


    /**
     * 构造函数，获得所有的视图解析器
     * @param viewResolverListProvider
     * @param serverCodecConfigurer
     */
    public GatewayConfig(ObjectProvider<List<ViewResolver>> viewResolverListProvider,
                         ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolverList = viewResolverListProvider.getIfAvailable(Collections::emptyList);

        this.serverCodecConfigurer = serverCodecConfigurer;
    }


    /**
     * 初始化限流参数
     */
    @PostConstruct
    public void initGatewayRules(){
        Set<GatewayFlowRule> rules = new HashSet<>();

        rules.add(
                // resource 是资源名称，与路由的资源对应
                new GatewayFlowRule("product_route")
                        //限流总数
                .setCount(1)
                        //时间窗口限定的时间，单位 秒
                .setIntervalSec(10));

        // 通过限流规则管理器，加载自定义的限流规则
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * 注册sentinel限流器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGlobalFilter(){
        return new SentinelGatewayFilter();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(){
        return new SentinelGatewayBlockExceptionHandler(viewResolverList, serverCodecConfigurer);
    }


    /**
     * 自定义限流页面
     */
    @PostConstruct
    public void initBlockHandler(){
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                Map<String, String> bodyMap = new HashMap<>();
                bodyMap.put("code", "0");
                bodyMap.put("message", "服务器繁忙，当前请求被限流。");
                return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(bodyMap));
            }
        };

        // 通过回调管理器，设置限流时的返回信息
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }


}
