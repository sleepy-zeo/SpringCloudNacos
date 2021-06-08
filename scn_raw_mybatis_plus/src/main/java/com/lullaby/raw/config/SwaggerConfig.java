package com.lullaby.raw.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // Swagger配置
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .alternateTypeRules(pageableTypeRule())
                .select() // select()接口返回ApiSelectorBuilder实例
                .apis(RequestHandlerSelectors.basePackage("com.lullaby")) //指定只显示该包路径下的url
                .paths(PathSelectors.any()) // 指定某些path不显示，这里显示所有
                .build();
    }

    // Swagger Apis的基本信息(http://localhost:xxxx/swagger-ui.html)
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Apis")
                .description("Api接口文档")
                .version("1.2.11")
                .termsOfServiceUrl("http://terms.com")
                .contact(new Contact("steven", "https://example.com", "xx@example.com"))
                .build();
    }

    private AlternateTypeRule pageableTypeRule() {
        return AlternateTypeRules.newRule(Pageable.class, Page.class, 1);
    }

    @ApiModel
    @Data
    public static class Page {
        @ApiModelProperty(value = "第page页，从0开始计数", example = "0")
        private Integer page;

        @ApiModelProperty(value = "每页数据数量", example = "10")
        private Integer size;

        @ApiModelProperty(value = "排序方式，比如id,asc")
        private List<String> sort;
    }
}
