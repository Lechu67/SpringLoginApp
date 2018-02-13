package app1.service;

import app1.model.GameEntity;
import app1.model.UserEntity;
import app1.repository.GameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameDAO gameDAO;

    public void createNewGame(char userSymbol,char computerSymbol, UserEntity userEntity, Enum difficulty){
        GameEntity gameEntity = new GameEntity(
                userSymbol,
                computerSymbol,
                getFirstPlayer(userSymbol,computerSymbol),
                userEntity,
                3,
                difficulty.toString());
        gameDAO.saveNewGame(gameEntity);
    }

    private char getFirstPlayer(char... symbols) {
        return symbols[(int) (Math.random() * symbols.length)];
    }

    public GameEntity loadGameByUser(UserEntity userEntity){
        return gameDAO.findGameByUserName(userEntity);
    }
}
