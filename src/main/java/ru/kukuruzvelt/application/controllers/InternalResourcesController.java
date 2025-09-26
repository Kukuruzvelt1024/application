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
import ru.kukuruzvelt.application.model.MovieEntity;
import java.io.*;

@Controller
public class InternalResourcesController {
    private static String sourceFolder = "B:\\src";
    private static String cssFolder = "B:\\IdeaProjects\\application\\src\\main\\resources\\templates\\css\\";
    private static String jsFolder = "B:\\IdeaProjects\\application\\src\\main\\resources\\templates\\js\\";
    private static String posterFolder = "B:\\posters\\";
    private static String videoSourceFolder;
    private static String assetsFolder;

    @Autowired
    private MyResourceHttpRequestHandler handler;

    @GetMapping("internal/{sourceType}/{fileName}")
    public void getFileFromLocalFileSystem(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable String sourceType,
            @PathVariable String fileName) throws IOException, ServletException {
        String sourcePath = null;
        if (sourceType.contentEquals("css")) sourcePath = cssFolder.concat(fileName);
        if (sourceType.contentEquals("javascript")) sourcePath = jsFolder.concat(fileName);
        if (sourceType.contentEquals("assets")) sourcePath = assetsFolder.concat(fileName);
        if (sourceType.contentEquals("posters")){
            DAO dao = DAO.getInstance(Application.sourceBase);
            MovieEntity me = dao.findByWebMapping(fileName);
            System.out.println("Отправка постера: " + me.getPosterPath());
            sourcePath = me.getPosterPath();
        }
        if (sourceType.contentEquals("file")){
            System.out.println("Отправка видеофрагмента: " + fileName + " на адрес: " + request.getRemoteAddr());
            sourcePath = DAO.getInstance(Application.sourceBase).
                    findByWebMapping(request.getRequestURL().
                            toString().
                            split("/file/")[1]).getFilePath();
        }
        File source = new File(sourcePath);
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, source);
        handler.filePath = sourcePath;
        handler.handleRequest(request, response);
    }

    @Component
    final static class MyResourceHttpRequestHandler extends ResourceHttpRequestHandler {
        private String filePath;

        private final static String ATTR_FILE = MyResourceHttpRequestHandler.class.getName() + ".file";
        @Override
        protected Resource getResource(HttpServletRequest request) throws IOException {
            final File file = (File) request.getAttribute(ATTR_FILE);
            return new FileSystemResource(new File(filePath));
        }
    }
}
