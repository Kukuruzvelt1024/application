package ru.kukuruzvelt.application;

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
import java.io.*;

@Controller
public class FileController {
    private static File sourceVideoFile = new File("B:\\src\\source.mp4");

    @Autowired
    private MyResourceHttpRequestHandler handler;

   @GetMapping("/file")
    public void getFile(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {
       request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, sourceVideoFile);
       handler.handleRequest(request, response);
   }
    @Component
    final static class MyResourceHttpRequestHandler extends ResourceHttpRequestHandler {

        private final static String ATTR_FILE = MyResourceHttpRequestHandler.class.getName() + ".file";
        @Override
        protected Resource getResource(HttpServletRequest request) throws IOException {
            final File file = (File) request.getAttribute(ATTR_FILE);
            return new FileSystemResource(sourceVideoFile);
        }

    }

}
