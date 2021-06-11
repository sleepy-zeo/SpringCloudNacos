package com.lullaby.druid.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    //配置Druid监控管理后台的servlet
    //内置Servlet容器时没有web.xml文件，所以使用springboot的注册servlet的方式
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //这些参数可以在com.alibaba.druid.support.http.StatViewServlet的父类com.alibaba.druid.support.http.ResourceServlet 中找到
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");//后台管理界面的登录账号
        initParams.put("loginPassword", "123456");//后台管理界面的登录密码
        //后台允许谁可以访问
        //initParams.put("allow","localhost");//表示只有本机可以访问
        initParams.put("allow", "");//为空或者为null时，表示允许所有访问
        //deny:Druid后台拒绝访问谁
        initParams.put("dz", "192.168.1.20");//表示禁止此ip访问
        //设置初始化参数
        bean.setInitParameters(initParams);
        return bean;
    }

    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource dataSource =  new DruidDataSource();
        dataSource.setFilters("stat");
        return dataSource;
    }
}
