package utils.file.checker;

/**
 * @author zhyu
 * @date 2019-05-28 09:51:30
 * 检测的最小单元 针对的是配置
 */
public class FileCheckersImpl implements FileChecker{

    private int type;

    private boolean ignoreCase;

    public boolean checkers(String src, String source) {
        switch (type) {
            case EQUALE_VALUE:
                return ignoreCase ? src.equalsIgnoreCase(source) : src.equals(source);
            case REG:
                break;
            case NGL:
                break;
        }
        return false;
    }

    //值相等
    public static final int EQUALE_VALUE = 1;
    //正则
    public static final int REG = 2;
    //其他表达式
    public static final int NGL = 3;

}
