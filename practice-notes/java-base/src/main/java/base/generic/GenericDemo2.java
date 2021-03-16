package base.generic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型extends super
 * <p>
 * 作为方法参数，<? extends T>类型和<? super T>类型的区别在于：
 * <? extends T>允许调用读方法T get()获取T的引用，但不允许调用写方法set(T)传入T的引用（传入null除外）；
 * <? super T>允许调用写方法set(T)传入T的引用，但不允许调用读方法T get()获取T的引用（获取Object除外）。
 * 一个是允许读不允许写，另一个是允许写不允许读
 */
@Slf4j
public class GenericDemo2 {

    @Test
    public void extendsDemo() {
        Pair<Integer> p = new Pair<>(123, 456);
        int n = add(p);
        log.info("n:{}", n);

        set(p);
    }

    /**
     * 使用类似<T extends Number>定义泛型类时表示：泛型类型限定为Number以及Number的子类
     */
    int add(Pair<? extends Number> p) {
        Number first = p.getFirst();
        Number last = p.getLast();
        return first.intValue() + last.intValue();
    }

    /**
     * 使用类似<? extends Number>通配符作为方法参数时表示：
     * 方法内部可以调用获取Number引用的方法，例如：Number n = obj.getFirst();；
     * 方法内部无法调用传入Number引用的方法（null除外），例如：obj.setFirst(Number n);。
     * 即一句话总结：使用extends通配符表示可以读，不能写。
     */
    int set(Pair<? extends Number> p) {
        Number first = p.getFirst();
        Number last = p.getLast();
        /*编译错误发生在p.setFirst()传入的参数是Integer类型。有些童鞋会问了，既然p的定义是Pair<? extends Number>，
        那么setFirst(? extends Number)为什么不能传入Integer？原因还在于擦拭法。如果我们传入的p是Pair<Double>，
        显然它满足参数定义Pair<? extends Number>，然而，Pair<Double>的setFirst()显然无法接受Integer类型。
        这就是<? extends Number>通配符的一个重要限制：方法参数签名setFirst(? extends Number)无法传递任何Number类型给setFirst(? extends Number)。
        这里唯一的例外是可以给方法参数传入null*/

//        p.setFirst(new Integer(first.intValue() + 100));  todo
        p.setLast(null);
        return p.getFirst().intValue() + p.getFirst().intValue();
    }


    @Test
    public void superDemo() {
        Pair<Number> p1 = new Pair<>(12.3, 4.56);
        Pair<Integer> p2 = new Pair<>(123, 456);
        this.setSame(p1, 100);
        this.setSame(p2, 200);
        log.info("p1:{}", p1.getFirst() + ", " + p1.getLast());
        log.info("p2:{}", p2.getFirst() + ", " + p2.getLast());
    }

    /**
     * 使用<? super Integer>通配符作为方法参数，表示方法内部代码对于参数只能写，不能读
     */
    void setSame(Pair<? super Integer> p, Integer n) {
        p.setFirst(n);
        p.setLast(n);
        Object first = p.getFirst();  // 唯一例外是可以获取Object的引用
//        Integer fri = p.getFirst(); // error  不允许调用get()方法获得Integer的引用。 todo

    }


    /**
     * PECS原则：Producer Extends Consumer Super。
     * 即：如果需要返回T，它是生产者（Producer），要使用extends通配符；如果需要写入T，它是消费者（Consumer），要使用super通配符
     */
    @Test
    public void testPECS() {
        List<Number> numbers = new ArrayList<>();
        List<Integer> nums = Arrays.asList(1, 9, 5, 3, 8, 14);
        this.copy(numbers, nums);
        log.info("copyResult:{}", numbers);
    }

    // 把src的每个元素复制到dest中:
    public <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i = 0; i < src.size(); i++) {
            T t = src.get(i);  // src是producer
            dest.add(t);    // dest是consumer
        }
    }


    /**
     * <?>通配符有一个独特的特点，就是：Pair<?>是所有Pair<T>的超类：
     */
    @Test
    public void test() {
        Pair<Integer> p = new Pair<>(123, 456);
        Pair<?> p2 = p; // 安全地向上转型
        System.out.println(p2.getFirst() + ", " + p2.getLast());
    }

    /**
     * 因为<?>通配符既没有extends，也没有super，因此：
     * 不允许调用set(T)方法并传入引用（null除外）；
     * 不允许调用T get()方法并获取T引用（只能获取Object引用）
     */
    static boolean isNull(Pair<?> p) {
        return p.getFirst() == null || p.getLast() == null;
    }



}
