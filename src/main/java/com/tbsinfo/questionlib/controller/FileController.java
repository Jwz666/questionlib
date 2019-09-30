package com.tbsinfo.questionlib.controller;

import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jayMamba
 * @date 2019/9/27
 * @time 12:10
 * @desc
 */
@RestController
public class FileController {

    @Value("${file.upload.path}")
    private String imagePath;
    /**
     * 图片上传
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/file/upload")
    public Object fileUpload(@RequestParam("upload") MultipartFile file,
                              HttpServletRequest request, HttpServletResponse response) {

        FileUpload uploadImageModel = new FileUpload();
        if (file.isEmpty()) {
           return new RetData().erro("500","请添加图片");
        } else {
            try {
                byte[] bytes = file.getBytes();
                String fileName = System.currentTimeMillis() + file.getOriginalFilename();
                String filePath = imagePath + fileName;
                Path path = Paths.get(filePath);
                Files.write(path, bytes);
                String fileUrl = request.getContextPath() + filePath;
                fileUrl = StringUtils.replace(fileUrl, "//", "/");
                uploadImageModel.setFileName(file.getOriginalFilename());
                uploadImageModel.setUrl("/image/" + fileName);
                uploadImageModel.setUploaded(1);
            } catch (IOException e) {
                e.printStackTrace();
               return new RetData().erro("500","上传出错");
            }
        }
        return uploadImageModel;
    }
}
