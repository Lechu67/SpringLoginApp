package app1.controller;

import app1.model.UserEntity;
import app1.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserDetailService service;
    private final Validator validator;

    @Autowired
    public RegistrationController(UserDetailService service, Validator validator) {
        this.service = service;
        this.validator = validator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @ModelAttribute("usercustom")
    public UserEntity createUserModel() {
        return new UserEntity();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpView(Model model) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(
            @ModelAttribute("usercustom") @Validated UserEntity userEntity,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        service.addUser(userEntity);
        return "redirect:/";
    }
}
