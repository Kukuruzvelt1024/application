package ru.kukuruzvelt.application.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kukuruzvelt.application.Application;
import ru.kukuruzvelt.application.domain.DAO;
import ru.kukuruzvelt.application.domain.MovieEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CatalogController {

    private String resourceFilesLocation = "B:\\src";
    private String resourceTextFilesLocation = "B:\\names";

    @GetMapping("/catalog")
    public String getCatalog(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model){
        File f = new File("B:\\src");
        File[] files = f.listFiles();
        List<String> videoList = new ArrayList<>();
        for (int i = 0; i < files.length; i++){
            videoList.add(files[i].getName().split(".mp")[0]);
        }


        model.addAttribute("videos", videoList);
        System.out.println();
        return "catalog";
    }

    @GetMapping("/catalogdb")
    public String getCatalogdb(Model model){
        DAO dao = new DAO(Application.sourceBase);
        List<MovieEntity> list = dao.getListOfEntities();

        model.addAttribute("videos", list);
        return "catalogdb";
    }




}
