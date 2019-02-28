package com.project.pan.myproject.dynamic;

/**
 * @author: panrongfu
 * @date: 2019/2/27 11:43
 * @describe: 代理对象,静态代理
 */
public class UserDaoProxy implements IUserDao {
    //接收保存目标对象
    private IUserDao target;

    @Override
    public void save() {
        System.out.println("开始事务...");
        target.save();//执行目标对象的方法
        System.out.println("提交事务...");
    }

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }
}
