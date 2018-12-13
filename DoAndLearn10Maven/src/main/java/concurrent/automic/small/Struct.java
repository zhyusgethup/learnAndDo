package concurrent.automic.small;

import gener.tempP.in.In;

import java.util.concurrent.*;

public class Struct {
        public static void main(String[] args) throws InterruptedException {
            ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    map.computeIfAbsent(1, e -> {
                        String valueForKey2 = map.get(2);
                        System.out.println("thread1 : get() returns with value for key 2 = " + valueForKey2);
                        String oldValueForKey2 = map.put(2, "newValue");
                        System.out.println("thread1 : after put() returns, previous value for key 2 = " + oldValueForKey2);
                        return map.get(2);
                    });
                }
            };

            Thread thread2 = new Thread() {
                @Override
                public void run() {
                    map.computeIfAbsent(2, e -> {
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        String value = "valueSetByThread2";
                        System.out.println("thread2 : computeIfAbsent() returns with value for key 2 = " + value);
                        return value;
                    });
                }
            };
           /* thread2.start();
            Thread.sleep(1000);
            thread1.start();*/
            ExecutorService pool = Executors.newFixedThreadPool(1);
           Future<Integer> re =  pool.submit(new A());
           try {
               if (re.get() != null) {
                   System.out.println(re.get());
               }
           }catch (Exception e){}
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
