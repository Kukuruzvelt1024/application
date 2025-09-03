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


import java.util.List;

@Controller
public class CatalogController {


    @GetMapping("/catalog")
    public String getCatalog(Model model){
        List<MovieEntity> list = new DAO(Application.sourceBase).getListOfEntities();
        model.addAttribute("MoviesList", list);
        return "catalog";
    }




}
