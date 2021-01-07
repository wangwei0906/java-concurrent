package lock_support;

import com.sun.deploy.net.proxy.ProxyUnavailableException;

import java.util.concurrent.locks.LockSupport;

/*
 * description:
 * author: W W
 * date：2021-01-06 10:57
 * */
public class Hocket1 {
   static Thread t2=null;
   static Thread t1=null;

    public static void main(String[] args) throws InterruptedException {
        final String num="12345678";
        final String word="abcdefgh";

        t2=new Thread(new Runnable() {
            public void run() {
                for(int i=0;i<word.length();i++){
                    LockSupport.park();
                    System.out.println(Thread.currentThread().getName()+"----->"+word.charAt(i));
                    LockSupport.unpark(t1);
                }
            }
        },"线程B");

        t1=new Thread(new Runnable() {
            public void run() {
                for(int i=0;i<num.length();i++){
                    System.out.println(Thread.currentThread().getName()+"----->"+num.charAt(i));
                    //这里后面两句的顺序不能该表
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        },"线程A");

        t1.start();
        t2.start();

    }
}
