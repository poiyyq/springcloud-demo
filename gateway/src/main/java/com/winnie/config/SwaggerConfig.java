package com.winnie.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关的swagger整合其他微服务的swagger接口文档
 */
@Primary
@Component
public class SwaggerConfig implements SwaggerResourcesProvider {
    @Autowired
    private ConfigConstants configConstants;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> list = new ArrayList<>();
        String swaggerResources = configConstants.swaggerResources;
        if (StringUtils.isNotBlank(swaggerResources)) {
            String[] resources = StringUtils.split(swaggerResources, ";");
            for (String resource : resources) {
                String[] param = StringUtils.split(resource, ",");
                list.add(swaggerResource(param[0], param[1], param[2]));
            }
        }
        return list;
    }

    /**
     * 创建swagger资源
     *
     * @param name     别名
     * @param location 路由，一定要跟zuul配置的前缀一致
     * @param version  版本号
     * @return
     */
    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
