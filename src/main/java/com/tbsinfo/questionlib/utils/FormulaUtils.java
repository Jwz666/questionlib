package com.tbsinfo.questionlib.utils;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author jayMamba
 * @date 2019/10/22
 * @time 10:55
 * @desc
 */
public class FormulaUtils {
//    /**
//     * <p>Description: xsl转换器</p>
//     */
//    public static String xslConvert(String s, String xslpath, URIResolver uriResolver){
//        TransformerFactory tFac = TransformerFactory.newInstance();
//        if(uriResolver != null)
//        {
//            tFac.setURIResolver(uriResolver);
//        }
//        StreamSource xslSource = new StreamSource(MathmlUtils.class.getResourceAsStream(xslpath));
//        StringWriter writer = new StringWriter();
//        try {
//            Transformer t = tFac.newTransformer(xslSource);
//            Source source = new StreamSource(new StringReader(s));
//            Result result = new StreamResult(writer);
//            t.transform(source, result);
//        } catch (TransformerException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return writer.getBuffer().toString();
//    }
//
//    /**
//     * <p>Description: 将mathml转为latx </p>
//     * @param mml
//     * @return
//     */
//    public static String convertMML2Latex(String mml){
//        mml = mml.substring(mml.indexOf("?>")+2, mml.length()); //去掉xml的头节点
//        URIResolver r = new URIResolver(){  //设置xls依赖文件的路径
//            @Override
//            public Source resolve(String href, String base) throws TransformerException {
//                InputStream inputStream = MathmlUtils.class.getResourceAsStream("/conventer/mml2tex/" + href);
//                return new StreamSource(inputStream);
//            }
//        };
//        String latex = xslConvert(mml, "/conventer/mml2tex/mmltex.xsl", r);
//        if(latex != null && latex.length() > 1){
//            latex = latex.substring(1, latex.length() - 1);
//        }
//        return latex;
//    }
//
//    /**
//     * <p>Description: office mathml转为mml </p>
//     * @param xml
//     * @return
//     */
//    public static String convertOMML2MML(String xml){
//        String result = xslConvert(xml, "/conventer/OMML2MML.XSL", null);
//        return result;
//    }
}
