package ru.kukuruzvelt.application;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class CatalogController {

    private String resourceFilesLocation = "B:\\src";

    @GetMapping("/catalog")
    public String getCatalog(Model model){
        File f = new File("B:\\src");
        //File[] files = File.listFiles(f);
        List<User> list = new ArrayList<>();
        list.add(new User("fds", "sdfsd"));
        list.add(new User("sdfsd", "aerter"));
        model.addAttribute("users", list);


        return "catalog";
    }
}
