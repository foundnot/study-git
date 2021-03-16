package spring.aop.proxy;

public class TeslaCarImpl implements Car{
    @Override
    public void add() {
        System.out.println("增加一辆Tesla车！！！");
    }

    @Override
    public void delete() {
        System.out.println("删除一辆Tesla车！！！");
    }

    @Override
    public void update() {
        System.out.println("更新一辆Tesla车！！！");
    }

    @Override
    public void query() {
        System.out.println("查询一辆Tesla车！！！");
    }
}
