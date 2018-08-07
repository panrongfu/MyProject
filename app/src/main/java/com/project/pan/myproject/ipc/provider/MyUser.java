package com.project.pan.myproject.ipc.provider;

/**
 * @author: panrongfu
 * @date: 2018/8/1 10:48
 * @describe:
 */

public class MyUser {
    public int id;
    public String name;
    public int sex;

    public MyUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
