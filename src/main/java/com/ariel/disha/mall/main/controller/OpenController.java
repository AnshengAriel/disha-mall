package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.config.app.AppProperties;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author ariel
 * @apiNote OpenController
 * @serial
 */
@RestController
@RequestMapping("/open")
public class OpenController {

    @Autowired
    private AppProperties appProperties;

    @GetMapping("/file/**")
    public HttpResponse<Void> getFile(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        Path path = Path.of(appProperties.getDataDir(), url.substring("/open/file".length()));
        if (Files.exists(path)) {
            try (InputStream in = Files.newInputStream(path, StandardOpenOption.READ)) {
                response.getOutputStream().write(in.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return HttpResponse.success();
        }else {
            return HttpResponse.error(HttpStatus.DATA_NOT_EXISTS);
        }
    }

}
