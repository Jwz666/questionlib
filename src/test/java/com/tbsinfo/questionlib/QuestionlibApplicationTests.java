package com.tbsinfo.questionlib;


import com.tbsinfo.questionlib.dao.BaseQuestionsMapper;
import com.tbsinfo.questionlib.model.BaseQuestions;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Autowired
    private BaseQuestionsMapper baseQuestionsMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPoi() throws Exception {
        File file = new File("E:/云题库标签/2019年浙江省中考数学分类汇编专题12：统计与概率（数据收集、整理与分析）_云题库标签.docx");
        XWPFDocument document = new XWPFDocument(new FileInputStream(file));
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        List<XWPFPictureData> pictures = document.getAllPictures();
        Map<String, String> pictureMap = new HashMap<>();
        StringBuffer contentBuffer = new StringBuffer();
        List<BaseQuestions> baseQuestionsList=new ArrayList<>();
        int type = 1;
        for (XWPFPictureData pictureData : pictures) {
            String id = pictureData.getParent().getRelationId(pictureData);
            String rawName = pictureData.getFileName();
            String fileExt = rawName.substring(rawName.lastIndexOf("."));
            String newName = System.currentTimeMillis() + UUID.randomUUID().toString() + fileExt;
            String path = imagePath + newName;
            File picFile = new File(path);
            FileOutputStream outputStream = new FileOutputStream(picFile);
            outputStream.write(pictureData.getData());
            outputStream.close();
            pictureMap.put(id, "/image/" + newName);
        }

        for (XWPFParagraph paragraph : paragraphs) {
            contentBuffer.append("<p>"+getText(paragraph, pictureMap)+"</p>");
        }
        String content=contentBuffer.toString();
        content=content.substring( content.indexOf("<p>标题"));
        String[] questionByType=content.split("<p>标题");
        for(int i=1;i<questionByType.length;i++ ) {
            if(questionByType[i].contains("选题")) {
                type=1;
            }
            if(questionByType[i].contains("填空")) {
                type=3;
            }
            if(questionByType[i].contains("简答")) {
                type=2;
            }
           String[] questionByNum = questionByType[i].split("<p>题号");
           for(int j=1;j<questionByNum.length;j++) {
               String questionContent=questionByNum[j].substring(questionByNum[j].indexOf("<p>题目"),questionByNum[j].indexOf("<p>答案"));
               String questionAnswer=questionByNum[j].substring(questionByNum[j].indexOf("<p>答案"),questionByNum[j].indexOf("<p>解析"));
               String questionAnalysis=questionByNum[j].substring(questionByNum[j].indexOf("<p>解析"));
               BaseQuestions baseQuestions=new BaseQuestions();
               baseQuestions.setQuestionType(type);
               baseQuestions.setSource(1);
               baseQuestions.setContent(questionContent);
               baseQuestions.setAnswer(questionAnswer);
               baseQuestions.setAnalysis(questionAnalysis);
               baseQuestions.setCreatedAt(new Date());
               baseQuestions.setUpdatedAt(new Date());
               baseQuestions.setStatus(0);
               baseQuestionsMapper.insert(baseQuestions);
           }
        }
//        for(String s:question) {
//            System.out.println(s);
//        }
    }

    String getText(XWPFParagraph paragraph,Map pictureMap) {
        String text="";
        List<XWPFRun> runs=paragraph.getRuns();
        for(XWPFRun run:runs) {
            if(run.getCTR().xmlText().indexOf("<w:drawing>")!=-1){
                String runXmlText = run.getCTR().xmlText();
                int rIdIndex = runXmlText.indexOf("r:embed");
                int rIdEndIndex = runXmlText.indexOf("/>", rIdIndex);
                String rIdText = runXmlText.substring(rIdIndex, rIdEndIndex);
                //System.out.println(rIdText.split("\"")[1].substring("rId".length()));
                String id = rIdText.split("\"")[1];
                text = text +"<img src = '"+pictureMap.get(id)+"'/>";
            }else{
                text = text + run;
            }

        }
        return text;
    }

    @Test
    public void  testStream() throws Exception {
        FileOutputStream fileOutputStream=new FileOutputStream("D:/test.txt");
        fileOutputStream.write("test".getBytes("UTF-8"));
        fileOutputStream.close();
    }

}
