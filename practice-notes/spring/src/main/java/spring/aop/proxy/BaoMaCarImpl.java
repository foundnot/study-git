package spring.aop.proxy;

public class BaoMaCarImpl implements Car{
    @Override
    public void add() {
        System.out.println("增加一辆宝马车！！！");
    }

    @Override
    public void delete() {
        System.out.println("删除一辆宝马车！！！");
    }

    @Override
    public void update() {
        System.out.println("更新一辆宝马车！！！");
    }

    @Override
    public void query() {
        System.out.println("查询一辆宝马车！！！");
    }
}
