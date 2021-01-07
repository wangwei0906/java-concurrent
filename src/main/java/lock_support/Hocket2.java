package lock_support;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/*
 * description:
 * author: W W
 * date：2021-01-06 13:37
 * */
public class Hocket2 {

    public static void main(String[] args) throws InterruptedException {
        final String num = "12345678";
        final String word = "abcdefgh";
        final Object obj=new java.lang.Object();

        new Thread(new Runnable() {
            public void run() {
                synchronized (obj){
                    for (int i = 0; i < word.length(); i++) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "----->" + word.charAt(i));
                        obj.notify();
                    }
                    obj.notify();
                }

            }
        }, "线程B").start();

        new Thread(new Runnable() {
            public void run() {
                synchronized (obj){
                    for (int i = 0; i < num.length(); i++) {
                        System.out.println(Thread.currentThread().getName() + "----->" + num.charAt(i));
                        obj.notify();
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    obj.notify();
                }

            }
        }, "线程A").start();

    }
}
