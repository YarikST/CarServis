package Test;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin-iorigins on 20.11.16.
 */
public class Tese {
    public Tese(String[] test){}
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        Run run = new Run();

        pool.invoke(run);

        run.reinitialize();

        TimeUnit.SECONDS.sleep(2);


        pool.invoke(run);

        TimeUnit.SECONDS.sleep(2);
    }

   static class Run extends RecursiveAction{
       @Override
       protected void compute() {
           System.out.println("run");
       }

       @Override
       public int hashCode() {
           return (int) (Math.random()*10);
       }

       @Override
       public boolean equals(Object obj) {
           return true;
       }
   }

}
