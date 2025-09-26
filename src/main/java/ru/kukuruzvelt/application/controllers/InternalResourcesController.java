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
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
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
    private static String posterFolder;
    private static String videoSourceFolder;
    private static String assetsFolder;

    @Autowired
    private MyResourceHttpRequestHandler handler;

    @GetMapping("/file/{name}")
    public void getVideoFile(HttpServletRequest request,
                             HttpServletResponse response,
                             @PathVariable String name)
            throws ServletException, IOException {
        System.out.println("Отправка видеофрагмента: " + name + " на адрес: " + request.getRemoteAddr());
        File source = new File(sourceFolder, name);
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, source);
        handler.handleRequest(request, response);

    }

    @GetMapping("/poster/{title}")
    public StreamingResponseBody getPoster(@PathVariable String title,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException {
        DAO dao = DAO.getInstance(Application.sourceBase);
        MovieEntity me = dao.findByWebMapping(title);
        System.out.println("Отправка постера: " + me.getPosterPath());
        final InputStream videoFileStream = new FileInputStream(me.getPosterPath());
        long size = videoFileStream.available();
        return (os) -> {
            readAndWrite(videoFileStream, os);
        };
    }

    @GetMapping("internal/{sourceType}/{fileName}")
    public StreamingResponseBody getFileFromLocalFileSystem(
            @PathVariable String sourceType,
            @PathVariable String fileName) throws IOException {


        final InputStream fileStream = new FileInputStream(findURLResourceInLocalStorage(sourceType, fileName));
        long size = fileStream.available();
        return (os) -> {readAndWrite(fileStream, os);
        };
    }

    private String findURLResourceInLocalStorage(String sourceType, String fileName){
        if (sourceType.contentEquals("css")) return new String(cssFolder + fileName);
        if (sourceType.contentEquals("javascript")) return new String(jsFolder + fileName);
        if (sourceType.contentEquals("assets")) return new String(assetsFolder + fileName);
        return null;
    }



    private void readAndWrite(final InputStream is, OutputStream os)
            throws IOException {
        byte[] data = new byte[1048576];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);

        }
        os.flush();
    }

    @Component
    final static class MyResourceHttpRequestHandler extends ResourceHttpRequestHandler {

        private final static String ATTR_FILE = MyResourceHttpRequestHandler.class.getName() + ".file";
        @Override
        protected Resource getResource(HttpServletRequest request) throws IOException {
            final File file = (File) request.getAttribute(ATTR_FILE);
            return new FileSystemResource(new File(DAO.getInstance(Application.sourceBase).
                    findByWebMapping(request.getRequestURL().
                            toString().
                            split("/file/")[1]).getFilePath()));
        }
    }
}
