package app1.config;

import app1.model.GameEntity;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("app1.service")
public class ServiceConfig {

}
