package com.winnie.config;

import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * seata所需代理数据的配置类
 */
@Configuration
public class SeataDataSourceProxyConfig {
    /**
     * 因为orm使用的是mybatis，所以需要在orm执行前被seata代理
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory dataSourceProxy(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(new DataSourceProxy(dataSource));
        return sqlSessionFactoryBean.getObject();
    }
}
