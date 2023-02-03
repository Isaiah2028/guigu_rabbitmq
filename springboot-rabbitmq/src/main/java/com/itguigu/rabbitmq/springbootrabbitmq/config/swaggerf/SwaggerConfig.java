//package com.itguigu.rabbitmq.springbootrabbitmq.config.swaggerf;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @Author: IsaiahLu
// * @date: 2023/2/2 21:07
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Bean
//    public Docket webApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("web")
//                .apiInfo(webApiInfo())
//                .select()
//                .build();
//    }
//    private ApiInfo webApiInfo() {
//        return new ApiInfoBuilder()
//                .title("rabbitmq接口文档")
//                .description("文档描述的rabbitmq微服务接口的接口定义").version("1.0")
//                .contact(new Contact("luyuan","http://www.luyuanasia","2244140752@qq.com")).build();
//    }
//
//}
