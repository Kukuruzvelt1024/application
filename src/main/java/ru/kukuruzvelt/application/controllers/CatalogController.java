package ru.kukuruzvelt.application.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CatalogController {

    private String resourceFilesLocation = "B:\\src";
    private String resourceTextFilesLocation = "B:\\names";

    @GetMapping("/catalog")
    public String getCatalog(Model model){
        File f = new File("B:\\src");
        File[] files = f.listFiles();
        List<String> videoList = new ArrayList<>();
        for (int i = 0; i < files.length; i++){
            videoList.add(files[i].getName().split(".mp")[0]);
        }


        model.addAttribute("videos", videoList);
        System.out.println(model.getClass());
        return "catalog";
    }


}
