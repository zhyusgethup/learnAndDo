package com.cellsgame;

import com.cellsgame.common.util.GameUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File Description.
 *
 * @author Yang
 */
public class TestGameUtil {

    public static Map<Integer, Integer> goods = GameUtil.createMap();// new ConcurrentHashMap();

    public static AtomicInteger addCount1 = new AtomicInteger(0);
    public static AtomicInteger addCount2 = new AtomicInteger(0);
    public static AtomicInteger delCount = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {
        goods.put(1,    10000);

        Thread t1 = new Thread(() -> {
            while (delCount.get() < 1000) {
                int count = delCount.getAndIncrement();
                System.out.println("11:" + count+ ",    "+ goods.get(1));
//                goods.computeIfPresent(1, )
                goods.compute(1, (k, v) -> {if (v >= 100) {v -= 100;} return v;});
                System.out.println("12:" + count+ ",    "+goods.get(1));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("end 1:" + goods.get(1));
        });

        Thread t2 = new Thread(() -> {
            while(addCount1.get() < 1000) {
                int count = addCount1.getAndIncrement();
                System.out.println("21:" + count+ ",    "+ goods.get(1));
                goods.compute(1, (k, v) -> {if (v >= 50) {v -= 50;} return v;});
                System.out.println("22:" + count+ ",    "+goods.get(1));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("end 2:" + goods.get(1));
        });


        Thread t3 = new Thread(() -> {
            while(addCount2.get() < 1000) {
                int count = addCount2.getAndIncrement();
                System.out.println("31:" + count+ ",    "+ goods.get(1));
                goods.compute(1, (k, v) -> v + 50);
//                goods.put(1, goods.get(1) + 50);
                System.out.println("32:" + count+ ",    "+goods.get(1));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("end 3:" + goods.get(1));
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
