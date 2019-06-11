package utils.file.checker;

import java.util.List;

public class CheckerTemplate {

    private String src;
    private CheckerTemplateType type;
    private List<Checker> checkerList;



    enum CheckerTemplateType {
        properties,
        xml
        ;
    }
    public class Checker {

    }
}
