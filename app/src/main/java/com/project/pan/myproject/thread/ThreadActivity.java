package com.project.pan.myproject.thread;

import android.os.AsyncTask;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.pan.myproject.R;

import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadActivity extends AppCompatActivity {

    final private int CANCELLED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        HandlerThread handlerThread = new HandlerThread("");
        new DownloadTask().execute("xxx","xxx","xxxx");
    }

    /**
     * 第一个参数Params，表示参数的类型
     * 第二个参数progress：表示后台任务的执行进度类型
     * 第三个参数result：表示后台任务的返回结果类型
     */
    class DownloadTask extends AsyncTask<String,Integer,Integer>{


        /**
         * 第一个先执行，主要是一些准备工作
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 第二个执行 执行异步任务
         * @param urls
         * @return
         */
        @Override
        protected Integer doInBackground(String... urls) {
          //调用publishProgress();该方法回调onProgressUpdate（...）方法
          //返回值就是onPostExecute（）的参数值
            if(isCancelled()){
                publishProgress(CANCELLED);
                return null;
            }
            return null;
        }

        /**
         * 第三个执行
         * 这个参数是 doInBackground的返回值
         * @param integer
         */
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        /**
         * 在主线程中执行，当后台任务的执行进度发生改变时，此方法会被调用
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
           switch (values[0]){
               case CANCELLED:
                   // TODO: 2019/2/28 停止任务 
                   break;
           }
        }

    }


    /***
     * 线程池
     */
    public void executor(){

        ExecutorService executorService = Executors.newCachedThreadPool();

        /**
         * Creates a new {@code ThreadPoolExecutor} with the given initial
         * parameters and default thread factory and rejected execution handler.
         * It may be more convenient to use one of the {@link Executors} factory
         * methods instead of this general purpose constructor.
         *
         * @param corePoolSize 核心线程数
         *        核心线程会一直存活，及时没有任务需要执行
         *        当线程数小于核心线程数时，即使有线程空闲，线程池也会优先创建新线程处理
         *        设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
         * @param maximumPoolSize 最大线程数
         *       当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务
         *       当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常
         * @param keepAliveTime 线程空闲时间
         *        当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
         *        如果allowCoreThreadTimeout=true，则会直到线程数量=0
         * @param unit 允许核心线程超时
         * @param workQueue 任务队列容量（阻塞队列）
         *        当核心线程数达到最大时，新任务会放在队列中排队等待执行
         * @param rejectedExecutionHandler 任务拒绝处理器
         *        当线程数已经达到maxPoolSize，切队列已满，会拒绝新任务
         *        当线程池被调用shutdown()后，会等待线程池里的任务执行完毕，再shutdown
         */
       // ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>())
    }
}
