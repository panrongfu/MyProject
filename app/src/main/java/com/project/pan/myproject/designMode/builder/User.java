package com.project.pan.myproject.designMode.builder;

import android.content.Context;

/**
 * Author: panrongfu
 * Date:2018/7/2 19:12
 * Description:建造者模式
 */

public class User {

    private  String name;
    private  int age;
    private  String phone;
    private  String address;

    //该方法私有（构造方法没有返回值）
    private User(Builder builder){
        this.name = builder.name;
        this.address = builder.address;
        this.age = builder.age;
        this.phone = builder.phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static Builder getBuilder(Context context){
        return new Builder(context);
    }

    public static class Builder{
        private final Context mContext;
        private String name;
        private int age;
        private String phone;
        private String address;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setAge(int age){
            this.age = age;
            return this;
        }

        public Builder setPhone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder setAddress(String address){
            this.address = address;
            return this;
        }

        public User bulid(){
            return new User(this);
        }
    }

}
