package app1.config;

import app1.computerMoveStrategy.ComputerMoveStrategy;
import app1.computerMoveStrategy.Difficulty;
import app1.computerMoveStrategy.EasyStrategy;
import app1.validator.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"app1.controller","app1.validator"})
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp().prefix("WEB-INF/view/");
    }
    @Bean
    public Validator validator(){
        return new UserValidator();
    }
}
