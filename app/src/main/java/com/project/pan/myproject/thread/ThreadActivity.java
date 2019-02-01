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
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadActivity extends AppCompatActivity {

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
    class DownloadTask extends AsyncTask<String,Long,Long>{


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
        protected Long doInBackground(String... urls) {
          //调用publishProgress();该方法回调onProgressUpdate（...）方法
          //返回值就是onPostExecute（）的参数值
            return null;
        }

        /**
         * 第三个执行
         * 这个参数是 doInBackground的返回值
         * @param aLong
         */
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }

        /**
         * 在主线程中执行，当后台任务的执行进度发生改变时，此方法会被调用
         * @param values
         */
        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

    }


    /***
     * 线程池
     */
    public void executor(){

        ExecutorService executorService = Executors.newCachedThreadPool();
        
    }
}
