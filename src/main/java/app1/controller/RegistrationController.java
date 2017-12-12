package app1.controller;

import app1.model.UserCustom;
import app1.service.UserDetailService;
import app1.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserDetailService service;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }
    @ModelAttribute("usercustom")
    public UserCustom createUserModel(){
        return new UserCustom();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpView(Model model){
        return "signup";
    }

    /*@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password) {

        service.addUser(new UserCustom(1,username,password,"USER"));
        return "redirect:/";
    }*/
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(
            @ModelAttribute("usercustom") @Validated UserCustom userCustom,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "signup";

        }
        service.addUser(new UserCustom(1,userCustom.getUsername(),userCustom.getPassword(),"USER"));
        return "redirect:/";
    }
}
