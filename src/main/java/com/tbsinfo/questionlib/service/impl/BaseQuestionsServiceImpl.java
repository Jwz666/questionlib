package com.tbsinfo.questionlib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.dao.QuelibQuestionTypeMapper;
import com.tbsinfo.questionlib.dao.QuestionTagsMapper;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.tbsinfo.questionlib.dao.BaseQuestionsMapper;
import com.tbsinfo.questionlib.model.QuelibQuestionType;
import com.tbsinfo.questionlib.model.QuestionTags;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.tbsinfo.questionlib.model.Tags;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * <p>
 * 题目数据表 服务实现类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@Service
public class BaseQuestionsServiceImpl extends ServiceImpl<BaseQuestionsMapper, BaseQuestions> implements BaseQuestionsService {

    @Value("${file.upload.path}")
    private String imagePath;

    @Autowired
    private BaseQuestionsMapper baseQuestionsMapper;
    @Autowired
    private QuelibQuestionTypeMapper quelibQuestionTypeMapper;
    @Autowired
    private QuestionTagsMapper questionTagsMapper;

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public IPage<BaseQuestions> getQuestionList(Page<BaseQuestions> page,BaseQuestions baseQuestions) {
        QueryWrapper<BaseQuestions> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("created_at");
        wrapper.setEntity(baseQuestions);
        return baseQuestionsMapper.selectPage(page, wrapper);
    }

    @Override
    public List<BaseQuestions> getQuestionList(BaseQuestions baseQuestions) {
        QueryWrapper<BaseQuestions> wrapper=new QueryWrapper<>();
        wrapper.setEntity(baseQuestions);
        return baseQuestionsMapper.selectList(wrapper);
    }

    @Override
    public List<QuelibQuestionType> getQuestionTypeList() {
        QueryWrapper<QuelibQuestionType> wrapper=new QueryWrapper<>();
        wrapper.setEntity(new QuelibQuestionType());
        return quelibQuestionTypeMapper.selectList(wrapper);
    }

    @Override
    public List<Tags> getTagsByQuestionId(Long id) {
        List<Tags> tagsList=questionTagsMapper.getTagsByQuestionId(id);
        return tagsList;
    }

    private List<Tags> getTagTree(List<Tags> tags) {
        List<Tags> rootNode=new ArrayList<>();
        for(Tags tag:tags) {
            if(tag.getParentId()==0) {
                rootNode.add(tag);

            }
        }
        fillChildNode(tags,rootNode);
        return rootNode;
    }

    private void fillChildNode(List<Tags> tagsSet,List<Tags> Nodes) {
        if(Nodes==null||Nodes.size()==0) {
            return ;
        }
        List<Tags> childTags=new ArrayList<>();
        for(Tags tag:Nodes) {
            for(Tags setTag:tagsSet) {
                if(setTag.getParentId()==tag.getId()) {
                    childTags.add(tag);
                }
            }
            fillChildNode(tagsSet,childTags);
            tag.setChildTags(childTags);
        }


    }

    @Transactional
    @Override
    public RetData addTags(Long questionId,List<Long> tagsList) {
        QueryWrapper<QuestionTags> wrapper=new QueryWrapper<>();
        QuestionTags questionTags=new QuestionTags();
        questionTags.setQuestionId(questionId);
        try {
            questionTagsMapper.delete(wrapper);
            for(Long id:tagsList) {
                QuestionTags questionTags1=new QuestionTags();
                questionTags1.setQuestionId(questionId);
                questionTags1.setTagId(id);
                questionTagsMapper.insert(questionTags1);
            }
            return new RetData().success(null);
        } catch (Exception e) {
            return new RetData().erro("500", "发生未知错误，请稍后重试");
        }


    }

    @Override
    public RetData addTags(QuestionTags questionTags) {
        int i=questionTagsMapper.insert(questionTags);
        if(i>0) {
            return new RetData().success(null);
        }
        return new RetData().erro("500", "发生未知错误，请稍后重试");
    }

    @Override
    public RetData deleteTags(QuestionTags questionTags) {
        QueryWrapper<QuestionTags> wrapper=new QueryWrapper<>();
        wrapper.setEntity(questionTags);
        questionTagsMapper.delete(wrapper);
        return new RetData().success(null);
    }

    @Override
    public BaseQuestions getMaxOne() {
        BaseQuestions baseQuestions = new BaseQuestions();
        baseQuestions.setId((long)baseQuestionsMapper.selectMax()); ;
        return baseQuestions;
    }


    /**
     * 导入word并解析至数据库
     * @param file
     * @return
     */
    @Override
    public RetData uploadWordToDB(MultipartFile file)  {
        try {
            //读取文件信息
            XWPFDocument document = new XWPFDocument( (FileInputStream)file.getInputStream());
            //获取所有行
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            //获取所有图片
            List<XWPFPictureData> pictures = document.getAllPictures();
            Map<String, String> pictureMap = new HashMap<>();
            StringBuffer contentBuffer = new StringBuffer();

            int type = 1;
            //将所有图片存至服务器
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
                //记录文件id以及文件路径
                pictureMap.put(id, "/image/" + newName);
            }

            for (XWPFParagraph paragraph : paragraphs) {
                //获取所有题目信息
                contentBuffer.append("<p>"+getText(paragraph, pictureMap)+"</p>");
            }
            //拆分
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
                    //对象化
                    BaseQuestions baseQuestions=new BaseQuestions();
                    baseQuestions.setQuestionType(type);
                    baseQuestions.setSource(1);
                    baseQuestions.setContent(questionContent);
                    baseQuestions.setAnswer(questionAnswer);
                    baseQuestions.setAnalysis(questionAnalysis);
                    baseQuestions.setCreatedAt(new Date());
                    baseQuestions.setUpdatedAt(new Date());
                    baseQuestions.setStatus(0);
                    //保存题目至数据库
                    baseQuestionsMapper.insert(baseQuestions);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new RetData().erro("500", "上传发生未知错误，请稍后重试");
        }
        return new RetData();
    }

    /**
     * 组装文本及图片
     * @param paragraph
     * @param pictureMap
     * @return
     */
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
}
