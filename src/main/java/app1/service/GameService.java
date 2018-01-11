package app1.service;

import app1.model.GameEntity;
import app1.model.UserEntity;
import app1.model.UserRole;
import app1.repository.GameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private UserDetailService userDetailService;

    public void createNewGame(String userSymbol){

        UserDetails currentUser =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = new UserEntity(
                currentUser.getUsername(),
                currentUser.getPassword()
        );
        GameEntity gameEntity = new GameEntity(userSymbol.charAt(0), getFirstPlayer(), userEntity,3);// dimension 3 by principe
        gameDAO.saveNewGame(gameEntity);
    }
    public GameEntity loadGameByUserName(String userName){
        return gameDAO.findGameByUserName(userName);
    }
    private boolean getFirstPlayer(){
        return new Random().nextBoolean();
    }
}
