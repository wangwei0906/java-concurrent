package concurrent_util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 赛马的实例
 * author: W W
 * date：2021-01-07 10:54
 * */
public class CyclicBarrierDemo2 {

    //定义一个运动员实例
    static class Athlete implements Runnable{
        //运动员的名称
        private String name;

        //运动员跑了多少米
        private int mileage;

        //定义个屏障
        private CyclicBarrier barrier;

        //产生一个的里程
        private static Random rand = new Random(47);

        public Athlete(String name,CyclicBarrier barrier){
            this.name=name;
            this.barrier=barrier;
        }

        public void run() {
            while (!Thread.interrupted()){
                mileage += rand.nextInt(10);
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public  String tracks() {
            StringBuilder s = new StringBuilder();
            for(int i = 0; i < this.mileage; i++) {
                s.append("*");
            }
            s.append(name);
            return s.toString();
        }
    }


    static class Track implements Runnable{
        public void run() {
            StringBuilder s = new StringBuilder();
            //打印赛道边界
            for(int i = 0; i < FINISH_LINE; i++) {
                s.append("=");
            }
            System.out.println(s);
            //打印赛马轨迹
            for(Athlete athlete : athletes) {
                System.out.println(athlete.tracks());
            }
            //判断是否结束
            for(Athlete athlete : athletes) {
                if(athlete.mileage >= FINISH_LINE) {
                    System.out.println(athlete.name + " IS WINER!");
                    exec.shutdownNow();
                    return;
                }
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static final int FINISH_LINE = 200;
    private static List<Athlete> athletes = new ArrayList<Athlete>();
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5, new Track());
        athletes.add(new CyclicBarrierDemo2.Athlete("王巍",barrier));
        athletes.add(new CyclicBarrierDemo2.Athlete("冠林",barrier));
        athletes.add(new CyclicBarrierDemo2.Athlete("梅斌",barrier));
        athletes.add(new CyclicBarrierDemo2.Athlete("老闫",barrier));
        athletes.add(new CyclicBarrierDemo2.Athlete("李伟",barrier));
        for(Athlete athlete:athletes){
            exec.execute(athlete);
        }

    }
}
