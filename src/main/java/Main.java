import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static String TEMPLATE_PATH = PropertiesUtils.getInstance().getProperty("templatePath");
    private static String OUTPUT_PATH = PropertiesUtils.getInstance().getProperty("outputPath");

    public static void main(String[] args) throws Exception {
        Element tempElement = ReadTemplate.getRootElement(TEMPLATE_PATH);


        InputStream inputStream = OutPutXML.class.getResourceAsStream("data.json");
        String jsonString = IOUtils.toString(inputStream, "utf8");
        JSONObject datas = JSONObject.parseObject(jsonString);

        // 创建Document
        Document document = DocumentHelper.createDocument();

        // 添加根节点
        Element root = document.addElement(tempElement.getName());


        List<DefaultAttribute> attributes = tempElement.attributes();
        for (DefaultAttribute attribute : attributes) {
            attribute.setValue(datas.getString(attribute.getName()));
        }
        root.attributes().addAll(attributes);
        addChildrenElement(tempElement, datas, root);

        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileOutputStream(new File(OUTPUT_PATH)), format);
        writer.write(document);

        System.out.println("dom4j CreateXML success!");

    }


    private static void addChildrenElement(Element tempElement, JSONObject datas, Element rootElement) {
        Iterator iterator = tempElement.elementIterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            if (element.getNodeType() == Node.ELEMENT_NODE) {
                String elementName = element.getName();
                if (element.hasContent()){
                    if ("message".equals(elementName)) {
                        addMessageValue(element, datas, rootElement);
                    } else if ("sign".equals(elementName)) {
                        addSignValue(element, datas, rootElement);
                    } else {
                        rootElement.addElement(elementName).setText(datas.getString(elementName));
                    }
                }else {
                    rootElement.addElement(elementName).setText(datas.getString(elementName));
                }

            }
        }

    }

    private static void addSignValue(Element element, JSONObject datas, Element rootElement) {
        JSONArray signs = datas.getJSONArray("sign");
        List<DefaultAttribute> attributes = element.attributes();
        for (int i = 0; i < signs.size(); i++) {
            JSONObject signInfo = signs.getJSONObject(i);
            Element sign = rootElement.addElement(element.getName());
            for (DefaultAttribute attribute : attributes) {
                attribute.setValue(signInfo.getString(attribute.getName()));
            }
            sign.attributes().addAll(attributes);
            addChildrenElement(element,signInfo,sign);
        }

    }

    private static void addMessageValue(Element element, JSONObject datas, Element root) {
        JSONArray messages = datas.getJSONArray("messages");
        List<DefaultAttribute> attributes = element.attributes();
        for (int i = 0; i < messages.size(); i++) {
            JSONObject messageInfo = messages.getJSONObject(i);
            Element message = root.addElement(element.getName());
            for (DefaultAttribute attribute : attributes) {
                attribute.setValue(messageInfo.getString(attribute.getName()));
            }
            message.attributes().addAll(attributes);
            addChildrenElement(element,messageInfo,message);
        }
    }
}
