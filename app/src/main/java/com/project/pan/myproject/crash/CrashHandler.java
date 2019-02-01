package com.project.pan.myproject.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: panrongfu
 * @date: 2018/12/7 11:28
 * @describe:
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler" ;
    private static final boolean DEBUG = true;
    private static final String PATH = Environment
            .getExternalStorageDirectory().getPath()+"/crashLog/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace" ;

    private static CrashHandler chInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private Context mContext;

    public CrashHandler() {
    }

    public static CrashHandler getInstance(){
        return chInstance;
    }

    public void init(Context context){
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 当程序中有未被捕获的 异常，系统会自动调用#uncaughtException方法
     * @param t  为出现未捕获的异常线程
     * @param e  为未捕获的异常，有了这个异常就可以获取到异常信息
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        //导出异常到sd卡
        dumpExceptionToSDCard(e);
        //这里 可以上传异常信息到服务器，方便开发人员分析日志从而解决bug
        uploadExceptionToServer();

        e.printStackTrace();
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则自己结束程序
        if(uncaughtExceptionHandler != null){
            uncaughtExceptionHandler.uncaughtException(t,e);
        }else {
            Process.killProcess(Process.myPid());
        }
    }

    private void uploadExceptionToServer() {
    }

    private void dumpExceptionToSDCard(Throwable ex) {
        //如果SD卡不存在，或者无法使用，则无法把异常信息写入SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir = new File(PATH);
            if (!dir.exists()){
                dir.mkdirs();
            }
            long current = System.currentTimeMillis();

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
            File file = new File(PATH+FILE_NAME+time+FILE_NAME_SUFFIX);
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                pw.println(time);
                dumpPhoneInfo(pw);
                pw.println();
                ex.printStackTrace(pw);
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }else {
            Log.w(TAG,"sdcard unmounted");
        }
    }

    /**
     * 手机信息
     * @param pw
     */
    private void dumpPhoneInfo(PrintWriter pw) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
            pw.print(pi.versionName);
            pw.print(pi.versionCode);
            //android版本号
            pw.print(Build.VERSION.RELEASE);
            //手机制造商
            pw.print(Build.MANUFACTURER);
            //手机型号
            pw.print(Build.MODEL);
            //cpu架构
            pw.print(Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
