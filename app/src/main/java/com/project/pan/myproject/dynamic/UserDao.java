package com.project.pan.myproject.dynamic;

/**
 * @author: panrongfu
 * @date: 2019/2/27 11:42
 * @describe: 代理目标对象
 */
public class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("----已经保存数据!----");
    }
}
