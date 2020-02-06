import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;
import test.JsonXmlUtils;

import java.io.*;

public class OutPutXML {
    private static String OUTPUT_PATH = PropertiesUtils.getInstance().getProperty("outputPath");

    public static void main(String[] args) throws IOException, SAXException {
       // createXml(new File(OUTPUT_PATH));
        InputStream inputStream = OutPutXML.class.getResourceAsStream("data.json");
        String jsonString = IOUtils.toString(inputStream, "utf8");
        JSONObject datas = JSONObject.parseObject(jsonString);

        Document document = JsonXmlUtils.jsonToDocument(datas);

        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileOutputStream(new File(OUTPUT_PATH)), format);
        writer.write(document);

    }

    private static void createXml(File file) {
        try {
            // 创建Document
            Document document = DocumentHelper.createDocument();

            // 添加根节点
            Element root = document.addElement("root");


            for (int i = 0; i < 3; i++) {
                Element person = root.addElement("person");
                person.addAttribute("name", "zhangsan" + i);

                person.addElement("age").addText(18 + i + "");
            }


            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(document);

            System.out.println("dom4j CreateDom4j success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
