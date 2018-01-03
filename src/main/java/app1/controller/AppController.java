package app1.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
public class AppController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootView(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return "index";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginView(Principal user) {
        if (user == null) {
            return "login";
        }
        return "redirect:/";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminView() {
        return "admin";
    }

    @RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
    public String tictactoeView() {
        return "tictactoe";
    }
}
