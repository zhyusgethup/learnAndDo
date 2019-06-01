package utils.file.xml;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;

public class XpathUtils {
    public static void main(String[] args) throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//实例化DocumentBuilderFactory对象
        DocumentBuilder bulider = dbf.newDocumentBuilder();
        Document doc =  bulider.parse(new FileInputStream("F:\\git\\learnAndDo\\DoAndLearn10Maven\\src\\main\\resources\\target\\setting.xml"));
        System.out.println(getStringFromXPath(doc,"/setting/webUrl"));
        System.out.println(getStringFromXPath(doc,"/setting/companyWebURL"));
    }

    public static String getStringFromXPath(Document w3cDoc,String xpath) throws Exception{


        //2.创建Document
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression expression = xPath.compile(xpath);
        //3.使用XPATH
        return expression.evaluate(w3cDoc);
//        List list = document.selectNodes();

//        //4.获取name的文本
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
    }
}
