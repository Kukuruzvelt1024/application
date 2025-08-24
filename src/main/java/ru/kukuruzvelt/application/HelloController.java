package ru.kukuruzvelt.application;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.Date;

@Controller
public class HelloController {

    @RequestMapping(value = "/greeting")
    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        System.out.println("Greeting mapping");
        return "greeting";
    }

    @GetMapping("/")
    public ResponseEntity<String> base(){
        return new ResponseEntity<String>("FG", HttpStatus.OK);
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

    @GetMapping("/video")
    public String videoController(Model model){


        return "video";
    }











}
