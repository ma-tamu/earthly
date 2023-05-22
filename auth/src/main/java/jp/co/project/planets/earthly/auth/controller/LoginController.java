package jp.co.project.planets.earthly.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
