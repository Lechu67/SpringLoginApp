package app1.validator;

import app1.model.UserCustom;
import app1.repository.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@PropertySource("classpath:application.properties")
public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return UserCustom.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","name.empty");

        UserCustom userCustom = (UserCustom) target;
        if (userCustom!=null){
            errors.rejectValue("username", "name.exist");
        }
    }
}
