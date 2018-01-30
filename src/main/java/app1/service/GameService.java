package app1.service;

import app1.model.GameEntity;
import app1.model.UserEntity;
import app1.repository.GameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameDAO gameDAO;

    public void createNewGame(String userSymbol){

        UserEntity userEntity = getUserEntity();
        GameEntity gameEntity = new GameEntity(userSymbol.charAt(0), isUserFirstPlayer(), userEntity,3);// dimension 3 by principe
        gameDAO.saveNewGame(gameEntity);
    }

    public GameEntity loadGameByCurrentUser(){
        return gameDAO.findGameByUserName(getUserEntity());
    }
    private boolean isUserFirstPlayer(){
        return new Random().nextBoolean();
    }

    private UserEntity getUserEntity() {
        UserDetails currentUser =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserEntity(
                currentUser.getUsername(),
                currentUser.getPassword()
        );
    }
}
