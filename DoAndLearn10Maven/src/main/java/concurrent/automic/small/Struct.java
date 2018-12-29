package concurrent.automic.small;

import gener.tempP.in.In;

import java.util.concurrent.*;
/***
 * computeIfAbsent 如果对应的key有值了，那么根本就不会走comput； 没有值，那么上锁走compute
 * @author Zeng Haoyu
 * 2018年12月18日 下午1:26:55
 */
public class Struct {
        public static void main(String[] args) throws InterruptedException {
            ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
//            map.put(2, "init value2");
            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    map.computeIfAbsent(1, e -> {
                        String valueForKey2 = map.get(2);
                        System.out.println("thread1 : get() returns with value for key 2 = " + valueForKey2);
                        try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
                        System.out.println("thread1 :before put() get=" + map.get(2));
                        String oldValueForKey2 = map.put(2, "newValue");
                        System.out.println("thread1 : after put() returns, previous value for key 2 = " + oldValueForKey2);
                        return map.get(2);
                    });
                }
            };

            Thread thread2 = new Thread() {
                @Override
                public void run() {
                    map.computeIfPresent(2, (k,v) -> {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("thread2 : k = " + k + "   v = " + v );
                        String value = "valueSetByThread2";
                        System.out.println("thread2 : computeIfAbsent() returns with value for key 2 = " + value);
//                        return value;
                        return value;
                    });
                }
//                @Override
//                public void run() {
//                    map.computeIfAbsent(2, k -> {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (Exception e1) {
//                            e1.printStackTrace();
//                        }
//                        System.out.println("thread2 : k = " + k);
//                        String value = "valueSetByThread2";
//                        System.out.println("thread2 : computeIfAbsent() returns with value for key 2 = " + value);
////                        return value;
//                        return null;
//                    });
//                    System.out.println("thread2 : outside value " + map.get(2));
//                }
            };
            Thread.sleep(200);
            thread1.start();
            thread2.start();
            Thread.sleep(4000);
            System.out.println(map);
         /*   ExecutorService pool = Executors.newFixedThreadPool(1);
           Future<Integer> re =  pool.submit(new A());
           try {
               if (re.get() != null) {
                   System.out.println(re.get());
               }
           }catch (Exception e){}*/
    }
    static class A implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("enter");
            Thread.sleep(3000);
            
            return 1;
        }
    }
}
