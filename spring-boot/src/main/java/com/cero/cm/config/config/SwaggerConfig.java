package com.cero.cm.config.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(TypeResolver typeResolver) {

        //Authentication header 처리를 위해 사용
        List global = new ArrayList();
        global.add(new ParameterBuilder().name("Authorization").
                description("Access Token").parameterType("header").
                required(false).modelRef(new ModelRef("string")).build());

        return new Docket(DocumentationType.OAS_30)
                .consumes(getConsumeContentTypes())
                .useDefaultResponseMessages(false)
                .alternateTypeRules(getAlternateTypeRuleForLocalDate())
                .alternateTypeRules(getAlternateTypeRuleForResponseEntity())
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(Pageable.class, Page.class)
//                .additionalModels(
//                        typeResolver.resolve(ActionMissionListRes.class)
//                        ,typeResolver.resolve(ActionMissionVO.class)
//                        ,typeResolver.resolve(ActionMissionCateListRes.class)
//                        ,typeResolver.resolve(ActionMissionCateVO.class)
//                )
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cero.cm"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("WalkPlanet")
                .version("0.1")
                .description("WalkPlanet Controller")
                .license("WalkPlanet")
                .build();
    }

    public AlternateTypeRule getAlternateTypeRuleForLocalDate() {
        TypeResolver typeResolver = new TypeResolver();
        return AlternateTypeRules.newRule(
                typeResolver.resolve(Map.class, String.class, typeResolver.resolve(LocalDate.class)),
                typeResolver.resolve(Map.class, String.class, Date.class),
                Ordered.HIGHEST_PRECEDENCE
        );
    }

    public AlternateTypeRule getAlternateTypeRuleForResponseEntity() {
        TypeResolver typeResolver = new TypeResolver();
        return AlternateTypeRules.newRule(
                typeResolver.resolve(ResponseEntity.class),
                typeResolver.resolve(Void.class)
        );
    }

    @Getter
    @Setter
    @ApiModel
    static class Page {
        @ApiModelProperty(value = "페이지 번호(0..N)")
        private Integer page;

        @ApiModelProperty(value = "페이지 크기", allowableValues="range[0, 10]")
        private Integer size;
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        consumes.add("multipart/form-data");
        consumes.add("application/octet-stream");

        return consumes;
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Bearer", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }




    //http://localhost:8089/swagger-ui/#/
}
