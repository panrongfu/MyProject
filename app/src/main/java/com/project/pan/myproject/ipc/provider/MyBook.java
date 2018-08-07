package com.project.pan.myproject.ipc.provider;

/**
 * @author: panrongfu
 * @date: 2018/8/1 10:10
 * @describe:
 */

public class MyBook {
    public int id;
    public String name;

    public MyBook() {
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

    @Override
    public String toString() {
        return "MyBook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
