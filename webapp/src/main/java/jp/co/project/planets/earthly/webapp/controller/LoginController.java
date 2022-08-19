package jp.co.project.planets.earthly.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * login controller
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("login");
    }
}
