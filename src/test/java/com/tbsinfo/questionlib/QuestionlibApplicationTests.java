package com.tbsinfo.questionlib;


import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionlibApplicationTests {

    @Value("${file.upload.path}")
    private String imagePath;


    @Test
    public void contextLoads() {
    }

    @Test
    public void testPoi() throws Exception {
        File file=new File("E:/云题库标签/2019年浙江省中考数学分类汇编专题12：统计与概率（数据收集、整理与分析）_云题库标签.docx");
        XWPFDocument document = new XWPFDocument(new FileInputStream(file));
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        List<XWPFPictureData> pictures = document.getAllPictures();
        Map<String,String> pictureMap=new HashMap<>();
        for(XWPFPictureData pictureData:pictures) {
            String id=pictureData.getParent().getRelationId(pictureData);
            String rawName = pictureData.getFileName();
            String fileExt = rawName.substring(rawName.lastIndexOf("."));
            String newName = System.currentTimeMillis() + UUID.randomUUID().toString() + fileExt;
            String path=imagePath+newName;
            File picFile=new File(path);
            FileOutputStream outputStream=new FileOutputStream(picFile);
            outputStream.write(pictureData.getData());
            outputStream.close();
            pictureMap.put(id, "/image/"+newName);
        }
    }
}
