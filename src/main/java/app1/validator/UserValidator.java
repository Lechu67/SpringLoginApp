package app1.validator;

import app1.model.UserCustom;
import app1.model.UserEntity;
import app1.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Transactional
public class UserValidator implements Validator {

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace
                (errors, "username", "username.empty", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace
                (errors, "password", "password.empty", "Required");

        UserEntity targetUser = (UserEntity) target;

        if (isUserExists(targetUser.getUsername())) {
            errors.rejectValue("username", "username.exist", "User already exist");
        }
    }

    private boolean isUserExists(String userName) {
        return userDAO.findByName(userName) != null;
    }
}
