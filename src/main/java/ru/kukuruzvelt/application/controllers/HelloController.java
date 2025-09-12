package ru.kukuruzvelt.application.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.kukuruzvelt.application.domain.DAO;
import ru.kukuruzvelt.application.domain.MovieEntity;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class HelloController {

    @RequestMapping(value = "/greeting")
    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        System.out.println("Greeting mapping");
        return "greeting";
    }

    @GetMapping("/")
    public String base(HttpServletRequest request){
        System.out.println("Access to Base PAGE from: " + request.getRemoteAddr());
        return "redirect:/catalog";
    }

    @GetMapping(value = "/index")
    public String Controller2(){
        System.out.println("Index mapping");
        return "index";

    }

    @GetMapping("/streamexample")
    public ResponseEntity<StreamingResponseBody> handleRbe() {
        StreamingResponseBody stream = out -> {
            String msg = "/srb" + " @ " + new Date();
            out.write(msg.getBytes());
        };
        return new ResponseEntity(stream, HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public String welcomePageMapping(Model model, HttpServletRequest request){
        System.out.println("Access to -welcome.html page from: " + request.getRemoteAddr() + "\n + " +
                request.getHeader("User-agent"));
        model.addAttribute("ip", request.getRemoteAddr().toString());
        model.addAttribute("useragent", request.getHeader("user-agent"));
        model.addAttribute("acceptlanguage", request.getHeader("accept-language"));
        model.addAttribute("controller", this.getClass());
        model.addAttribute("method", this.getClass().getMethods()[0]);
        System.out.println("Get Mapping Executed in class" + this.getClass().toString());
        return "welcome";
    }

    @GetMapping("/reader")
    public String reader(){
        DAO dao = new DAO("B:\\names\\database_substitute.txt");
        List<MovieEntity> list = dao.getListOfEntities();
        Iterator<MovieEntity> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("Find by WebMapping");
        System.out.println(dao.findByWebMapping("twelvemonkeys"));
        System.out.println(dao.findByWebMapping("first_blood"));
        System.out.println("Find by Name");
        System.out.println(dao.findByOriginalTitle("12 monkeys"));
        System.out.println(dao.findByOriginalTitle("First Blood"));
        System.out.println("Find by FilePath");
        System.out.println(dao.findByFilePath("B:\\src\\twelve_monkeys.mp4"));
        System.out.println(dao.findByFilePath("B:\\src\\rambo.mp4"));
        System.out.println("Find by Russian");
        System.out.println(dao.findByRussianTitle("Рэмбо. Первая Кровь"));
        System.out.println(dao.findByRussianTitle("12 Обезьян"));


        return "index";
    }














}
