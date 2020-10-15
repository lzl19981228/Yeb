package com.xxxx.server.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//指定哪些包生成接口文档
				.apis(RequestHandlerSelectors.basePackage("com.xxxx.server.controller"))
				//该包下所有路径
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> results = new ArrayList<>();
		results.add(getContextPath("/hello/.*"));
		return results;
	}

	private SecurityContext getContextPath(String regexPath) {
		return SecurityContext.builder()
				//授权路径
				.forPaths(PathSelectors.regex(regexPath))
				//授权引用
				.securityReferences(defaultAuth())
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		List<SecurityReference> results = new ArrayList<>();
		AuthorizationScope scope = new AuthorizationScope("global","access Everything");
		AuthorizationScope[] scopes = new AuthorizationScope[1];
		scopes[0] = scope;
		results.add(new SecurityReference("authorization",scopes));
		return results;
	}


	private Predicate<String> getPath(String regexPath) {
		return PathSelectors.regex(regexPath);
	}


	private List<? extends SecurityScheme> securitySchemes() {
		List<ApiKey> results = new ArrayList<>();
		//apikey的名字，参数名字，参数所在位置
		results.add(new ApiKey("Authorization", "Authorization", "header"));
		return results;
	}

	/**
	 * 文档描述信息
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.version("v1")
				.contact(new Contact("xxxx", "localhost:8081", "xxx@xxx.com"))
				.description("云E办接口文档")
				.title("云E办接口文档")
				.build();
	}

}