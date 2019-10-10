package com.tbsinfo.questionlib.controller;

import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.FileUpload;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    @Value("${file.wordTemplate.path}")
    private String templatePath;

    @Autowired
    private BaseQuestionsService baseQuestionsService;

    Logger logger= LoggerFactory.getLogger(this.getClass());

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


    /**
     * 上传word并解析
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadWord")
    public RetData uploadWord(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        return baseQuestionsService.uploadWordToDB(multipartFile);
    }

    /**
     * 下载模板
     * @param response
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) {
        File file = new File(templatePath);
        String fileName=templatePath.substring(templatePath.lastIndexOf("/")+1);
        if(file.exists()) {
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
            response.setCharacterEncoding("UTF-8");
            FileInputStream fileInputStream=null;
            BufferedInputStream bufferedInputStream=null;
            OutputStream outputStream=null;
            byte[] bytes=new byte[1024];
            try{
                fileInputStream=new FileInputStream(file);
                bufferedInputStream=new BufferedInputStream(fileInputStream);
                outputStream=response.getOutputStream();
                int i=bufferedInputStream.read(bytes);
                while(i!=-1) {
                    outputStream.write(bytes,0,i);
                    i=bufferedInputStream.read(bytes);
                }

            } catch (Exception e) {
                logger.info(e.getMessage());
            } finally {
                try {
                    fileInputStream.close();
                    fileInputStream.close();
                    bufferedInputStream.close();
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }


            }
        }
    }

}
