package com.zhuang.quartz.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XmlUtils {

    public static Document getDocument(String filePath) {
        Document document;
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(new File(filePath));
        } catch (Exception ex) {
            throw new RuntimeException("读取配置文件“" + filePath + "”出错", ex);

        }
        return document;
    }
}
