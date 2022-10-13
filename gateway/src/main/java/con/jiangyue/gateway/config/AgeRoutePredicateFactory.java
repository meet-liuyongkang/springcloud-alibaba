package con.jiangyue.gateway.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author liuyongkang
 * @date Create in 2022/10/12 14:28
 *
 * 自定义断言工厂
 * 实现只对年龄范围在 20~30 岁直接的用户转发
 *
 * 注意：类名需要为 XxxRoutePredicateFactory 格式，Xxx 即为配置文件中的参数名
 */
@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    public AgeRoutePredicateFactory() {
        super(AgeRoutePredicateFactory.Config.class);
    }

    /**
     * 将配置文件中的属性值，绑定到配置类中
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        // 这里的顺序需要和配置文件中顺序一致
        return Arrays.asList("minAge", "maxAge");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                // 从请求中获取参数
                String age = serverWebExchange.getRequest().getQueryParams().getFirst("age");

                if(StringUtils.isNoneBlank(age)){
                    int ageParam = Integer.parseInt(age);
                    if (ageParam > config.minAge && ageParam < config.maxAge){
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     * 定义一个内部类，用来接收配置信息
     *
     * 内部类应该是static的，否则无法初始化
     */
    @Data
    public static class Config {
        private Integer minAge;
        private Integer maxAge;
    }
}
