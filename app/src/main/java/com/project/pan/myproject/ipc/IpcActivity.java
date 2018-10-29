  package com.project.pan.myproject.ipc;

  import android.annotation.SuppressLint;
  import android.content.ComponentName;
  import android.content.ContentValues;
  import android.content.ContextWrapper;
  import android.content.Intent;
  import android.content.ServiceConnection;
  import android.database.Cursor;
  import android.net.Uri;
  import android.os.AsyncTask;
  import android.os.Bundle;
  import android.os.Handler;
  import android.os.IBinder;
  import android.os.Message;
  import android.os.Messenger;
  import android.os.RemoteException;
  import android.os.SystemClock;
  import android.support.v7.app.AppCompatActivity;
  import android.text.TextUtils;
  import android.util.Log;
  import android.view.View;

  import com.project.pan.myproject.R;
  import com.project.pan.myproject.ipc.provider.MyBook;
  import com.project.pan.myproject.ipc.provider.MyUser;

  import java.io.BufferedReader;
  import java.io.BufferedWriter;
  import java.io.IOException;
  import java.io.InputStreamReader;
  import java.io.OutputStreamWriter;
  import java.io.PrintWriter;
  import java.net.Socket;
  import java.text.SimpleDateFormat;
  import java.util.Date;

  /**
 * @author pan
 */
public class IpcActivity extends AppCompatActivity {

    private IBookManager iBookManager;
    private Messenger mMessenger;
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private final int MESSAGE_SOCKET_CONNECTED = 1;
    private final int MESSAGE_RECEIVE_NEW_MSG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_SOCKET_CONNECTED:
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                         while(!IpcActivity.this.isFinishing()){
                            String content = br.readLine();
                            Log.e("receive:", content);
                            if(content != null){
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                                //获取当前时间
                                Date date = new Date(System.currentTimeMillis());
                                String timeStr = simpleDateFormat.format(date);
                                final String showMsg = "from server:"+content+"time"+timeStr;
                                mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showMsg)
                                        .sendToTarget();
                            }
                         }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);

    }

    public void clickAidl(View view){
        Intent aidlIntent = new Intent(this,AidlService.class);
        bindService(aidlIntent,aidlConnection,BIND_AUTO_CREATE);
    }

    public void clickIntentService(View view){
        Intent intentService = new Intent(this,MyIntentService.class);
        startService(intentService);
    }

    public void clickMessengerService(View view){
        Intent messengerIntent = new Intent(this,MessengerService.class);
        bindService(messengerIntent,messengerConnection,BIND_AUTO_CREATE);
    }

    public void clickContentProvider(View view){
        Uri bookUri = Uri.parse("content://com.project.pan.myproject.book.provider/book");
        ContentValues values = new ContentValues();
        values.put("id",6);
        values.put("name","java从入门到放弃");
        //插入一条数据
        getContentResolver().insert(bookUri,values);

        /**
         * 第一个参数，uri，rui是什么呢？好吧，上面我们提到了Android提供内容的叫Provider，
         * 那么在Android中怎么区分各个Provider？有提供联系人的，有提供图片的等等。
         * 所以就需要有一个唯一的标识来标识这个Provider，Uri就是这个标识，
         * android.provider.ContactsContract.Contacts.CONTENT_URI
         * 就是提供联系人的内容提供者，可惜这个内容提供者提供的数据很少。
         *
         * 第二个参数，projection，真不知道为什么要用这个单词，
         * 这个参数告诉Provider要返回的内容（列Column），
         * 比如Contacts Provider提供了联系人的ID和联系人的NAME等内容，
         * 如果我们只需要NAME，那么我们就应该使用：
         *
         * 第三个参数，selection，设置条件，相当于SQL语句中的where。null表示不进行筛选。
         * 如果我们只想返回名称为张三的数据，第三个参数应该设置为
         *
         * 第四个参数，selectionArgs，这个参数是要配合第三个参数使用的，如果你在第三个参数里面有？
         * 那么你在selectionArgs写的数据就会替换掉？
         *
         * 第五个参数，sortOrder，按照什么进行排序，相当于SQL语句中的Order by。
         * 如果想要结果按照ID的降序排列
         */
        Cursor bookCursor = getContentResolver().query(bookUri,new String[]{"id","name"},null,null,null);
        while (bookCursor.moveToNext()){
            MyBook myBook = new MyBook();
            myBook.id = bookCursor.getInt(0);
            myBook.name = bookCursor.getString(1);
            Log.e("IPC","book:"+myBook.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.project.pan.myproject.book.provider/user");
        Cursor userCursor = getContentResolver().query(userUri,
                new String[]{"id","name","sex"},
                null,
                null,
                null);
        while (userCursor.moveToNext()){
            MyUser user = new MyUser();
            user.id = userCursor.getInt(0);
            user.name = userCursor.getString(1);
            user.sex = userCursor.getInt(2);
            Log.e("Ipc","user:"+user.toString());
        }
        userCursor.close();
    }

    public void clickSocket(View view){
        AsyncTask.execute(()-> connectTcpServer());
    }

    public void clickSendSocket(View view){
        mPrintWriter.println("我发送一个信息吧，说什么呢");
    }

    private void connectTcpServer(){
        Socket socket = null;
        while (socket == null){
            try {
                socket = new Socket("localhost",10086);
                mClientSocket = socket;
                //输出流
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                //读服务端数据
                BufferedReader br = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                while(!IpcActivity.this.isFinishing()){
                    String content = br.readLine();
                    Log.e("receive:", content);
                    if(content != null){
                        // HH:mm:ss
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        //获取当前时间
                        Date date = new Date(System.currentTimeMillis());
                        String timeStr = simpleDateFormat.format(date);
                        final String showMsg = "from server:"+content+"time"+timeStr;
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showMsg)
                                .sendToTarget();
                    }
                }
                mPrintWriter.close();
                br.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
            }
        }

    }

    ServiceConnection aidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //asInterface方法用于将服务端的Binder对象转换成客户端所需要的AIDL接口类型的对象
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                /**
                 * 客户端调用远程服务的方法，被调用的方法运行在服务端的binder线程池中，同时客户端线程会被挂起
                 * 这时候如果如果服务端方法执行比较耗时，就会导致客户端线程长时间地阻塞在这里
                 * 如果这个线程是UI线程的话，就会导致客户端ANR
                 * 所以在调用服务端的方法适合，尽量在子线程中调用（Handler）
                 */
                iBookManager.addBook(new Book());
                //获取书集 iBookManager.getBookList();
                //添加监听
                iBookManager.registerListener(onNewBookAddListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    IOnNewBookAddListener onNewBookAddListener = new IOnNewBookAddListener.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNewBookAdd(Book newBook) throws RemoteException {

        }
    };

    ServiceConnection messengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Message msg = Message.obtain(null,0);
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello,this is client");
            msg.setData(bundle);
            //这里设置，就是为了服务端回复客户端
            msg.replyTo = getReplyMessenger;
            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Messenger getReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Log.e("handleMessage","get data from serve");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        //解注册
        unbindService(aidlConnection);
        unbindService(messengerConnection);
        try {
            iBookManager.unRegisterListener(onNewBookAddListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    public void broadcastReceiver(){

    }
}
