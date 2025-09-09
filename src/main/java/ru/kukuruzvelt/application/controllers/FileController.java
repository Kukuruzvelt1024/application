package ru.kukuruzvelt.application.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import ru.kukuruzvelt.application.Application;
import ru.kukuruzvelt.application.domain.DAO;
import ru.kukuruzvelt.application.domain.MovieEntity;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileController {
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

    @GetMapping("/poster/{title}")
    public StreamingResponseBody getPoster(@PathVariable String title,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException {
        DAO dao = new DAO(Application.sourceBase);
        MovieEntity me = dao.findByWebMapping(title);
        System.out.println(me.getPosterPath());
        final InputStream videoFileStream = new FileInputStream(me.getPosterPath());
        long size = videoFileStream.available();
        return (os) -> {
            readAndWrite(videoFileStream, os);
        };
    }

    @GetMapping("/assets/r")
    public StreamingResponseBody getAsset(
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException {
        final InputStream videoFileStream = new FileInputStream("B:\\assets\\sda.jpg");
        long size = videoFileStream.available();
        System.out.println("Asset loaded");
        return (os) -> {
            readAndWrite(videoFileStream, os);
        };
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
            return new FileSystemResource(new File(new DAO(Application.sourceBase).
                    findByWebMapping(request.getRequestURL().
                            toString().
                            split("/file/")[1]).getFilePath()));
        }
    }
}
