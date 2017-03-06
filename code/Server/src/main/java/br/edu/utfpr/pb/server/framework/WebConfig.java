package br.edu.utfpr.pb.server.framework;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Edson on 07/02/2017.
 * More docs in: https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")//"http://domain2.com")
                .allowedMethods("POST", "GET", "OPTIONS", "DELETE")
//                .allowedHeaders("header1", "header2", "header3")
//                .exposedHeaders("header1", "header2")
                .allowCredentials(true).maxAge(3600);
    }
}
