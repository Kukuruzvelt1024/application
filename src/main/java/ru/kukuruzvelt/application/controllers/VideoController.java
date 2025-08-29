package ru.kukuruzvelt.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class VideoController {


    @GetMapping("/video/{name}")
    public String videoController(Model model, @PathVariable String name){
        String path = name;
        model.addAttribute("pageTitle", name);
        path = "file/" + path;
        model.addAttribute("page", path);
        System.out.println("page:" + path);
        return "video";
    }


}
