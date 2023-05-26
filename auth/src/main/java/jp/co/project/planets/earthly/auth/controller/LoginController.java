package jp.co.project.planets.earthly.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * login controller
 */
@Controller
public class LoginController {

    /**
     * ログイン画面
     * 
     * @return ログイン画面
     */
    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
