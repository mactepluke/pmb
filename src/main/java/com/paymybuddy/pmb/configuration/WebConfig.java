package com.paymybuddy.pmb.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Log4j2
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${web.cors.allowed-origins}")
    private String allowedOrigins;
    @Value("#{'${web.cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;
    @Value("${web.cors.max-age}")
    private int maxAge;
    @Value("${web.cors.allowed-headers}")
    private List<String> allowedHeaders;
    @Value("${web.cors.exposed-headers}")
    private String exposedHeaders;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders(CorsConfiguration.ALL);
    }


//    @Bean
//    public WebMvcConfigurer corsMappingConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(allowedOrigins)
//                        .allowedMethods(String.valueOf(allowedMethods))
//                        .maxAge(maxAge)
//                        .allowedHeaders(String.valueOf(allowedHeaders))
//                        .exposedHeaders(exposedHeaders);
//            }
//        };
//    }

  /*  @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods(CorsConfiguration.ALL)
                        .allowedHeaders(CorsConfiguration.ALL)
                        .allowedOriginPatterns(CorsConfiguration.ALL);
            }
        };
    }*/


}
