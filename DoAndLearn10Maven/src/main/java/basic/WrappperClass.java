package basic;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.ws.api.server.SDDocument;

/**
 * @author zhyu
 * @date 2019/5/13 16:16
 * desc: 测试Integer.equals
 */

public class WrappperClass {
    public static void main(String[] args) {
        Order order = new Order();
        order.status = Order.SD;
        System.out.println(order.status.equals(Order.OK));
    }
}
class Order {
    Integer status = 0;
    public static final Integer OK = 5;
    public static final Integer SD = 4;
}
