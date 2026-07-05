package com.ariel.disha.mall.main.controller;

import cn.hutool.core.util.StrUtil;
import com.ariel.disha.mall.config.app.AppProperties;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.HttpStatus;
import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.consts.SymbolConst;
import com.ariel.disha.mall.util.DateTimeUtil;
import com.ariel.disha.mall.util.RandomUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * @author ariel
 * @apiNote FileController
 * @serial
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AppProperties appProperties;

    @PostMapping("/upload")
    public HttpResponse<String> getFile(MultipartFile file) {
        int index;
        if (StrUtil.isBlank(file.getOriginalFilename()) || (index = file.getOriginalFilename().lastIndexOf(SymbolConst.POINT)) == NumberConst.INT_1_NEG) {
            return HttpResponse.error(HttpStatus.PARAM_ERROR);
        }
        String fileName = RandomUtil.randomString(NumberConst.INT_4) + file.getOriginalFilename().substring(index);
        Path filePath = Path.of(DateTimeUtil.currDateStr(), fileName);
        Path absPath = Path.of(appProperties.getDataDir()).resolve(filePath);
        if (Files.exists(absPath)) {
            return HttpResponse.error(HttpStatus.FILE_IS_EXISTS);
        }
        try {
            Files.createDirectories(absPath.getParent());
            file.transferTo(absPath);
        } catch (IOException e) {
            log.error("", e);
            return HttpResponse.error(HttpStatus.FILE_UPLOAD_FAILED);
        }
        return HttpResponse.success(filePath.toString().replace(SymbolConst.BACKSLASH, SymbolConst.SLASH));
    }

}
