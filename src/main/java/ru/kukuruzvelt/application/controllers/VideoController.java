package ru.kukuruzvelt.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kukuruzvelt.application.Application;
import ru.kukuruzvelt.application.domain.DAO;
import ru.kukuruzvelt.application.domain.MovieEntity;


@Controller
public class VideoController {

    @GetMapping("/movie/{name}")
    public String videoController1(Model model, @PathVariable String name){
        try {
            MovieEntity me = new DAO(Application.sourceBase).findByWebMapping(name);
            System.out.println("Movie Mapping \n" + me.toString());
            model.addAttribute("pageTitle", me.getTitleRussian());
            model.addAttribute("page", "file/" + name);
            return "video";
        }
        catch (NullPointerException npe){
            return "404";
        }
    }


}
