package com.wuyiccc.tianxuan.gateway.filter;

import com.wuyiccc.tianxuan.gateway.config.TianxuanGatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.management.timer.TimerNotification;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/7/1 16:47
 */
@Slf4j
@Component
public class SecurityJWTFilter implements GlobalFilter, Ordered {

    @Autowired
    private TianxuanGatewayConfig tianxuanGatewayConfig;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> excludeUrls = tianxuanGatewayConfig.getUrls();

        String url = exchange.getRequest().getURI().getPath();

        if (CollectionUtils.isNotEmpty(excludeUrls)) {
            for (String excludeUrl : excludeUrls) {
                if (antPathMatcher.matchStart(excludeUrl, url)) {
                    return chain.filter(exchange);
                }
            }
        }
        log.info("被拦截了...");

        return chain.filter(exchange);
    }

    /**
     * 数值越小, 优先级越大
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
