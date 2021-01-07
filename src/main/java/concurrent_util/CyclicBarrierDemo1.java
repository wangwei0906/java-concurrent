package concurrent_util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * description: 运动员-赛跑示例
 * author: W W
 * date：2021-01-07 10:23
 * */
public class CyclicBarrierDemo1 {
    //首先定义个运动员类

    static class Athlete implements Runnable{

        //屏障
        private CyclicBarrier barrier;

        //运动员姓名
        private String name;

        public Athlete(CyclicBarrier barrier,String name){
            this.barrier=barrier;
            this.name=name;
        }

        public void run() {
            System.out.println(name+"就位");
            try {
                //等待所有运动员就位
                barrier.await();

                //其他线程全部就位了，再开始跑
                Random random=new Random();
                double time=random.nextDouble()+9;
                System.out.println(name+":"+time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    private static Executor executor = Executors.newFixedThreadPool(8);

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(8, new Runnable() {
        public void run() {
            System.out.println("全部运动员已就位，鸣枪开跑.....");
        }
    });

    public static void main(String[] args) {
        List<Athlete> athletes=new ArrayList<Athlete>();
        athletes.add(new Athlete(cyclicBarrier,"博尔特"));
        athletes.add(new Athlete(cyclicBarrier,"鲍威尔"));
        athletes.add(new Athlete(cyclicBarrier,"盖伊"));
        athletes.add(new Athlete(cyclicBarrier,"布雷克"));
        athletes.add(new Athlete(cyclicBarrier,"加特林"));
        athletes.add(new Athlete(cyclicBarrier,"苏炳添"));
        athletes.add(new Athlete(cyclicBarrier,"路人甲"));
        athletes.add(new Athlete(cyclicBarrier,"路人乙"));
        for(Athlete athlete:athletes){
            executor.execute(athlete);
        }

    }
}
