package com.ydlclass.core;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 这个类负责存储所有的配置信息
 */
public class Configuration {

    public static final int PORT;
    public static final String BASE_URL;

    //
    public static final List<String> FILTERS = new ArrayList<>();

    // 存放数据源的信息
    private static final Map<String, String> DATA_SOURCE = new ConcurrentHashMap<>(8);

    // 放置servlet对应的配置
    private static final Map<String, String> SERVLETS = new ConcurrentHashMap<>(8);

    public static final List<String> getFilters() {
        return FILTERS;
    }

    public static Map<String, String> getDataSource() {
        return DATA_SOURCE;
    }

    public static Map<String, String> getSERVLETS() {
        return SERVLETS;
    }

    static {
        // 获取文档对象
        URL url = Thread.currentThread().getContextClassLoader().getResource("web.xml");
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(url);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //
        if (document != null) {
            // 获取根节点
            Element root = document.getRootElement();

            // 读取数据源的相关信息
            Element dataSource = root.element("data-source");
            // iterate through child elements of root
            for (Iterator<Element> it = dataSource.elementIterator(); it.hasNext();) {
                Element element = it.next();
                // do something
                Attribute name = element.attribute("name");
                Attribute value = element.attribute("value");
                DATA_SOURCE.put(name.getValue(), value.getValue());
            }

            // 读取servlets的相关信息
            Element servlets = root.element("servlets");
            // iterate through child elements of root
            for (Iterator<Element> it = servlets.elementIterator(); it.hasNext();) {
                Element element = it.next();
                // do something
                Element name = element.element("url");
                Element value = element.element("servlet-class");
                SERVLETS.put(name.getText(), value.getText());
            }

            // 读取filters的相关信息
            Element filters = root.element("filters");
            // iterate through child elements of root
            for (Iterator<Element> it = filters.elementIterator(); it.hasNext();) {
                Element element = it.next();
                // do something
                FILTERS.add(element.getText());
            }

            // 给PORT赋值
            Element port = root.element("port");
            PORT = Integer.parseInt(port.getText());

            Element baseUrl = root.element("base-url");
            BASE_URL = baseUrl.getText();
        } else {
            // 默认值
            PORT = 8080;
            BASE_URL = "D://";
        }

    }

}
