package com.phyl;

import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by xh on 2017/4/7.
 */
@Controller
public class FileController {
    String filePath = "H://upload//";

    @RequestMapping("/")
    public String toIndex() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("myfile") MultipartFile file) {
        if (!ObjectUtils.isEmpty(file)) {
            upload(file);
            return "successful";
        } else {
            return "file is empty....";
        }
    }

    @PostMapping("/batch/upload")
    @ResponseBody
    public String uploadFiles(HttpServletRequest request) {
        List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
        fileList.stream().filter(file -> !ObjectUtils.isEmpty(file)).forEach(this::upload);
        return "successful";
    }

    private void upload(MultipartFile file) {
        String filename = file.getOriginalFilename();
        File dest = new File(filePath + filename);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
