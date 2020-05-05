package com.winnie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 统一配置类，接收配置中心数据
 */
@Component
public class ConfigConstants {
    @Value("${swagger.resources}")
    String swaggerResources;
}
