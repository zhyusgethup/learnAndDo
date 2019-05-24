package math.money;

import java.math.BigDecimal;

/**
 * @author zhyu
 * @date 2019/5/9 16:04
 *
 * desc:
 */

public class BigDecimalDemo {
    public static void main(String[] args) {
        BigDecimal zero1 = BigDecimal.ZERO;
        zero1 = zero1.add(new BigDecimal(1));
        System.out.println(zero1);
        System.out.println(BigDecimal.ZERO);
        System.out.println("------------------");
        Person person1 = new Person(100.0);
        BigDecimal money = person1.money;
        money.subtract(new BigDecimal(1));
        System.out.println(person1.money);
        System.out.println("------------------");
        Person p2 = new Person(0);
        p2.money = person1.money;
        System.out.println(person1.money);
        person1.money = BigDecimal.ZERO;
        System.out.println(person1.money);
        System.out.println(p2.money);
        System.out.println("-------------add--------");
        BigDecimal a1 = new BigDecimal(1);
        BigDecimal a2 = new BigDecimal(2);
        BigDecimal a3 = new BigDecimal(-2);
        System.out.println(a1.add(a2).add(a3));
        System.out.println("--------------------------------");
        BigDecimal bb = new BigDecimal(3);
        BigDecimal bb2 = new BigDecimal(-3);
        System.out.println(bb.add(bb2));
    }

    static class Person {
        private BigDecimal money;
        Person(double value) {
            this.money = new BigDecimal(value);
        }
    }


}
