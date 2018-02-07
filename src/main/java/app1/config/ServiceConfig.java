package app1.config;

import app1.computerMoveStrategy.ComputerMoveStrategy;
import app1.computerMoveStrategy.Difficulty;
import app1.computerMoveStrategy.EasyStrategy;
import app1.model.GameEntity;
import app1.service.BoardService;
import app1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan({
        "app1.service",
        "app1.computerMoveStrategy"
})
public class ServiceConfig {

    @Bean
    public Map<Difficulty,ComputerMoveStrategy> computerMoveStrategyMap (){
        Map<Difficulty,ComputerMoveStrategy> map = new HashMap<>();
        map.put(Difficulty.EASY,new EasyStrategy());
        return map;
    }

}
