package lesson6;


import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

public class SingalTest {

    private static int SUM;

    public static void main(String[] args) {
        // 5个面包师傅，同时启动
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    while (true) {
                        synchronized (SingalTest.class) {
                            if (SUM + 3 > 100) {
                                SingalTest.class.wait();
                            } else {
                                SUM += 3;
                                System.out.println(Thread.currentThread().getName() + "生产了面包，库存：" + SUM);
                                Thread.sleep(500);
                                SingalTest.class.notify();//随机通知一个wait方法阻塞线程
//                            SingalTest.class.notifyAll();
                            }
                        }
                        Thread.sleep(200);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            },"面包师傅【"+i+"】").start();
        }


        //20个消费者，同时启动
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    while (true) {
                        synchronized (SingalTest.class) {
                            if (SUM - 1 < 0) {
                                SingalTest.class.wait();
                            } else {
                                SUM--;
                                System.out.println(Thread.currentThread().getName() + "消费了面包，库存：" + SUM);
                                Thread.sleep(500);
                                SingalTest.class.notify();
//                            SingalTest.class.notifyAll();
                            }
                        }
                        Thread.sleep(200);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            },"消费者【"+i+"】").start();
        }
    }
}
