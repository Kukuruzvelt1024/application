package ru.kukuruzvelt.application;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;

@Controller
public class CatalogController {

    private String resourceFilesLocation = "B:\\src\\Фильмы";

    @GetMapping("/catalog")
    public String getCatalog(Model model){
        File f = new File("B:\\src\\Фильмы");
        //File[] files = File.listFiles(f);


        return "catalog";
    }
}
