package lock_support;

/*
 * description:
 * author: W W
 * date：2021-01-06 14:23
 * */
public class Hocket3 {

    static class NumHolder {
        String num="123456789";
        String word="abcdefghi";

        synchronized void numEach() throws InterruptedException {
            for(int i=0;i<num.length();i++){
                System.out.println(Thread.currentThread().getName()+"---->"+num.charAt(i));
                notify();
                wait();
            }
        }

        synchronized void wordEach() throws InterruptedException {
            for(int i=0;i<word.length();i++){
                wait();
                System.out.println(Thread.currentThread().getName()+"---->"+word.charAt(i));
                notify();
            }
        }
    }


    public static void main(String[] args) {
        final NumHolder holder=new NumHolder();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        holder.numEach();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"数字线程").start();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        holder.wordEach();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"字母线程").start();
    }


}
