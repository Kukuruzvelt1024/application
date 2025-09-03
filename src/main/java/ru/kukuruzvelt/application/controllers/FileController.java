package ru.kukuruzvelt.application.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import ru.kukuruzvelt.application.Application;
import ru.kukuruzvelt.application.domain.DAO;

import java.io.*;

@Controller

public class FileController {
    private static File sourceVideoFile = new File("B:\\src\\Video File Example.mp4");
    private static String sourceFolder = "B:\\src";

    @Autowired
    private MyResourceHttpRequestHandler handler;


    @GetMapping("/file/{name}")
    public void getSomeFile(HttpServletRequest request,
                        HttpServletResponse response, @PathVariable String name)
            throws ServletException, IOException {
        File source = new File(sourceFolder, name);
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, source);
        handler.handleRequest(request, response);
        System.out.println(name + " file requested");
    }


    @Component
    final static class MyResourceHttpRequestHandler extends ResourceHttpRequestHandler {

        private final static String ATTR_FILE = MyResourceHttpRequestHandler.class.getName() + ".file";
        @Override
        protected Resource getResource(HttpServletRequest request) throws IOException {
            System.out.println(request.getRequestURL());
            System.out.println();
            DAO dao = new DAO(Application.sourceBase);

            final File file = (File) request.getAttribute(ATTR_FILE);
            return new FileSystemResource(new File(sourceFolder,
                    request.getRequestURL().
                            toString().
                            substring(30))+".mp4");
            //Данный код привязан к полному URI.
        }

    }

}
