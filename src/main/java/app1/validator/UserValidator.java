package app1.validator;

import app1.model.UserCustom;
import com.mysql.cj.api.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.SessionAttribute;

@Component
@PropertySource("classpath:application.properties")
public class UserValidator implements Validator {

    @Value("${name.empty}")
    private String NAME_EMPTY;


    @Override
    public boolean supports(Class<?> clazz) {
        return UserCustom.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username",NAME_EMPTY);


       /* UserCustom userCustom = (UserCustom) target;
        if (userCustom!=null){
            errors.rejectValue("username", "name.exist");
        }*/
    }
}
