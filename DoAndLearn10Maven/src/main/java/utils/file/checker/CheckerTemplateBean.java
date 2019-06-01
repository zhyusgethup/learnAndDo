package utils.file.checker;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XStreamAlias("root")
public class CheckerTemplateBean implements Serializable {
    private static final long serialVersionUID = 567119860357020081L;
    @XStreamAlias("template")
    private List<Template> templateList;
    class Template {
        @XStreamAsAttribute
        private String src;
        @XStreamAsAttribute
        private String type;
        private List<Checker> checkers;

    }

    class Checker {
        @XStreamAsAttribute
        private String xpath;
    }

    public static void main(String[] args) {
        CheckerTemplateBean checkerTemplateBean = toBean("F:\\git\\learnAndDo\\DoAndLearn10Maven\\src\\main\\resources\\teamplate\\fileCheckerTemplate\\testTemplate.xml", CheckerTemplateBean.class);

    }
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        try {
            XStream xstream = new XStream(new DomDriver());
            //忽略不需要的节点
            xstream.ignoreUnknownElements();
            //对指定的类使用Annotations 进行序列化
            xstream.processAnnotations(cls);
            T obj = (T) xstream.fromXML(xmlStr);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
