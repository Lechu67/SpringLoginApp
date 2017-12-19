package app1.controller;

import app1.model.UserCustom;
import app1.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserCustom createUserModel() {
        return new UserCustom();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpView(Model model) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(
            @ModelAttribute("usercustom") @Validated UserCustom userCustom,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        service.addUser(userCustom);
        return "redirect:/";
    }
}
