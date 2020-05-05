package com.winnie.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

//@Component
public class TokenFilter extends ZuulFilter {
    // 过滤类型， pre表示执行前
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否启动此过滤器
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤内容
     * 假如此时请求头不携带token， 则拦截一切请求
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        String token = request.getHeader("token"); // 令牌
        if (StringUtils.isBlank(token)) {
            // 没有令牌，拒绝访问
            currentContext.setSendZuulResponse(false);
            HttpServletResponse response = currentContext.getResponse();
            currentContext.setResponseBody("request header not find token, service deny");
            currentContext.setResponseStatusCode(401);
            return null;
        }
        // 有令牌，继续访问
        return null;
    }
}
