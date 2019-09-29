package com.tbsinfo.questionlib.utils;

import com.tbsinfo.questionlib.model.BaseQuestions;

import java.util.List;

/**
 * @author jayMamba
 * @date 2019/9/27
 * @time 14:42
 * @desc
 */
public class HTMLUntil {
    //从html中提取纯文本  
    public static String StripHT(String strHtml) {
        String txtcontent = strHtml.replaceAll("</?[^>]+>","");//剔出<html>的标签    
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>","");//去除字符串中的空格,回车,换行符,制表符    
        return txtcontent;
    }

    public static void dealBaseQuestionList(List<BaseQuestions> list) {
        for(BaseQuestions b : list) {
           String simple=StripHT(b.getContent());
           if(simple.length()>10) {
               simple=simple.substring(0,10)+"...";
           } else if(simple.length()==0){
              simple="题干为空";
           } else {
               simple=simple.substring(0,simple.length()-1)+"...";
           }
           b.setContent(simple);
        }
    }

}
