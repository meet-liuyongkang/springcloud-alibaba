package con.jiangyue.gateway.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author liuyongkang
 * @date Create in 2022/10/12 15:44
 *
 * 自定义全局过滤器（所有请求都生效）
 *
 */
@Component
public class TokenGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if(StringUtils.isBlank(token)){
            System.out.println("鉴权失败");

            // 设置异常响应码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 直接返回，不再执行后面的过滤器
            return exchange.getResponse().setComplete();
        }
        // 如果有正确的token，则继续执行后面的过滤器
        return chain.filter(exchange);
    }

    /**
     * 顺序，值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
