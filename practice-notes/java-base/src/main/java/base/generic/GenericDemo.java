package base.generic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GenericDemo {

    @Test
    public void extendsGeneric(){
        this.getParentGeneric();
    }

    @Test
    public void generic1(){
        this.generic();
    }

    @Test
    public void genericWipe(){

        // 无法取得带泛型的Class
        // 因为T是Object，我们对Pair<String>和Pair<Integer>类型获取Class时，获取到的是同一个Class，也就是Pair类的Class
        // 换句话说，所有泛型实例，无论T的类型是什么，getClass()返回同一个Class实例，因为编译后它们全部都是Pair<Object>。
        Pair<String> p1 = new Pair<>("Hello", "world");
        Pair<Integer> p2 = new Pair<>(123, 456);
        System.out.println(p1.getClass() == p2.getClass()); // true  带不同的泛型  获得Class是一样的
        System.out.println(p1.getClass()==Pair.class); // true

        // 无法判断带泛型的类型
        // 不存在Pair<String>.class，而是只有唯一的Pair.class
        Pair<Integer> p = new Pair<>(123, 456);
//        if (p instanceof Pair<String>) {      // Compile error:
//        }

        // 不能实例化T类型：
       /* public class Pair<T> {
            private T first;
            private T last;
            public Pair() {
                // Compile error:
                first = new T();
                last = new T();
            }
        }*/
    }

    // 泛型编写
    private void generic(){
        List<PersonInfo> personList = new ArrayList<PersonInfo>(){{
            this.add(new PersonInfo("aa", 1, "01-01"));
            this.add(new PersonInfo("bb", 12, "03-01"));
            this.add(new PersonInfo("cc", 7, "04-01"));
            this.add(new PersonInfo("dd", 6, "02-01"));
        }};

        Arrays.sort(personList.toArray());
        log.info("list===:{}", personList.toString());
//        personList.sort(Comparator.comparing(Person::getName));
        log.info("list2===:{}", personList.toString());
    }

    //子类可以获取父类的泛型类型<T>
    private void getParentGeneric(){
        Class<IntPair> clazz = IntPair.class;
        Type t = clazz.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments(); // 可能有多个泛型类型
            Type firstType = types[0]; // 取第一个泛型类型
            Class<?> typeClass = (Class<?>) firstType;
            log.info("typeClass:{}", typeClass);
        }
    }

    //不能直接实例化T类型 要实例化T类型，我们必须借助额外的Class<T>参数
    public class genericInstance<T> {
        private T first;
        private T last;

//        public genericInstance(){
//            // Compile error:
//            first = new T();
//            last = new T();
//        }

        public genericInstance(Class<T> clazz) throws Exception{
            //要实例化T类型，我们必须借助额外的Class<T>参数
            first = clazz.newInstance();
            last = clazz.newInstance();
        }
    }

    static class IntPair extends Pair<Person> {
        public IntPair(Person first, Person last) {
            super(first, last);
        }
    }


}
