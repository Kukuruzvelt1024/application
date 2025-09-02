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


    @GetMapping("/video/{name}")
    public String videoController(Model model, @PathVariable String name){
        String path = name;
        model.addAttribute("pageTitle", name);
        path = "file/" + path;
        model.addAttribute("page", path);
        System.out.println("Access to Video Mapping + " + "page:" + path);
        return "video";
    }

    @GetMapping("/movie/{name}")
    public String videoController1(Model model, @PathVariable String name){
        DAO dao = new DAO(Application.sourceBase);
        MovieEntity me = dao.findByWebMapping(name);
        System.out.println("Movie Mapping \n" + me.toString());
        model.addAttribute("pageTitle", me.getTitleRussian());
        model.addAttribute("filePath", me.getFilePath());
       // model.addAttribute(null, null);
        return "video";
    }


}
