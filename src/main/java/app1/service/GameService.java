package app1.service;

import app1.model.GameEntity;
import app1.model.UserEntity;
import app1.repository.GameDAO;
import org.springframework.security.core.context.SecurityContextHolder;

//@Service
public class GameService {

//    @Autowired
    private GameDAO gameDAO;

    public void createNewGame(char symbol){
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameEntity gameEntity = new GameEntity(symbol, true, currentUser);
        gameDAO.saveNewGame(gameEntity);
    }
    public GameEntity loadGameByUserName(String userName){
        return gameDAO.findGameByUserName(userName);
    }
}
