package ru.kukuruzvelt.application;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcomePageMapping(Model model, HttpServletRequest request){
        System.out.println("Access to -welcome.html page from: " + request.getRemoteAddr() + "\n + " +
                request.getHeader("User-agent"));
        model.addAttribute("ip", request.getRemoteAddr().toString());
        model.addAttribute("useragent", request.getHeader("user-agent"));
        model.addAttribute("acceptlanguage", request.getHeader("accept-language"));
        model.addAttribute("controller", this.getClass());
        model.addAttribute("method", this.getClass().getMethods()[0]);
        System.out.println("Get Mapping Executed in class" + WelcomeController.class.toString());
        return "welcome";
    }
}
