package app1.service;

import app1.model.GameEntity;
import app1.model.UserEntity;
import app1.repository.GameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameDAO gameDAO;

    public void createNewGame(String userSymbol, UserEntity userEntity){
        GameEntity gameEntity = new GameEntity(userSymbol.charAt(0), isUserFirstPlayer(), userEntity,3);
        gameDAO.saveNewGame(gameEntity);
    }
    public GameEntity loadGameByCurrentUser(UserEntity userEntity){
        return gameDAO.findGameByUserName(userEntity);
    }
    private boolean isUserFirstPlayer(){
//        return new Random().nextBoolean();
        return false;
    }
}
