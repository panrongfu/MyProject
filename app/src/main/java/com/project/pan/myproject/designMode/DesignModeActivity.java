package com.project.pan.myproject.designMode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.project.pan.myproject.R;
import com.project.pan.myproject.designMode.annotation.BindName;
import com.project.pan.myproject.designMode.annotation.BindText;
import com.project.pan.myproject.designMode.annotation.Person;
import com.project.pan.myproject.designMode.builder.User;
import com.project.pan.myproject.designMode.service.MyIntentService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class DesignModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mode);

      //  User user = new User.Builder(this).bulid();

      User user = User.getBuilder(this)
              .setAddress("广西壮族自治区")
              .setAge(12)
              .setPhone("158754411")
              .setName("蜡笔小新")
              .bulid();

      Toast.makeText(this,user.toString(),Toast.LENGTH_LONG).show();

      initAnnotaion();
      initIntentService();
    }
    private void initAnnotaion() {

        try {
            Class clazz = Person.class;
            Method method = clazz.getMethod("show", String.class);
            //获取方法上的注解
            BindName annotation = method.getAnnotation(BindName.class);
            //获取注解属性值
            System.out.print(annotation.name()+""+annotation.age());
            // TODO: 2018/7/2 取到值就可以根据业务做数据处理
            Log.e("BindName",annotation.name()+"<:>"+annotation.age());
            method.invoke(new Person(),"AA");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        try {
            Class clazz2 = Class.forName("com.example.pan.advanced.designMode.annotation.Test");
            Field[] fields = clazz2.getFields();
            for(Field field:fields){
            BindText bindText = field.getAnnotation(BindText.class);
            if(bindText !=null){
                String value = bindText.testValue();
                Log.e("bindText",value);
            }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void initIntentService() {

        MyIntentService.startActionBaz(this,"aaa","bbb");
    }
}
