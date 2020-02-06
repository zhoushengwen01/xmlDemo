import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ReadTemplate {
    private static String TEMPLATE_PATH = PropertiesUtils.getInstance().getProperty("templatePath");

    public static void main(String[] args) {

        parseXML(new File(TEMPLATE_PATH));
    }

    public static Element getRootElement(String filePath) {
        SAXReader reader = new SAXReader();
        try {
            return reader.read(new File(filePath)).getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void parseXML(File file) {
        try {
            // 创建dom4j解析器
            SAXReader reader = new SAXReader();

            // 获取Document节点
            Document document = reader.read(file);
            System.out.println("Root element :" + document.getRootElement().getName());

            // 递归打印xml文档信息
            parseElement(document.getRootElement());

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void parseElement(Element rootElement) {
        Element element;
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()) {

            element = (Element) iterator.next();


            //标签中的属性
            List<DefaultAttribute> attributes = element.attributes();
            for (DefaultAttribute attribute : attributes) {
                System.out.println(attribute.getName());
                System.out.println(attribute.getValue());
            }

            //标签的名称和值
            System.out.println("Current Element Name :" + element.getName() + " , Text :" + element.getTextTrim());


            if (element.getNodeType() == Node.ELEMENT_NODE) {
                if (element.hasContent()) {
                    parseElement(element);
                }
            }
        }
    }

}
