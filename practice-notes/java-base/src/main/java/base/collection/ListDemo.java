package base.collection;



import base.reflex.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 有序(先后顺序存放) 可重复
 * ArrayList   查询快 添加/删除慢 内存占用小
 * LinkList    查询慢 添加/删除快 内存占用大
 */
@Slf4j
public class ListDemo {

    @Test
    public void testContains(){
//        List<String> strList = new ArrayList<>();
//        strList.add("a");
//        strList.add("b");
//        strList.add("c");
//        log.info("基本类型testContains：{}", strList.contains("a"));  // true
//        log.info("基本类型testContains：{}", strList.contains("A"));  // false

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("a"));
        studentList.add(new Student("b", 10));

        //内容相同，但不一定是同一个对象（equals相同才是一个对象）
        log.info("引用类型testContains：{}", studentList.contains(new Student("a"))); // 没有重写Student的equals方法 返回false;

        log.info("引用类型testContains：{}", studentList.contains(new Student("a"))); // 重写Student的equals方法 返回true;

    }

    @Test
    public void testRemoveIf() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(4);
        list2.add(3);
        list2.add(5);

        list2.removeIf(list1::contains);

        log.info("list2:{}", list2.toString());

        List<Person> list3 = new ArrayList<>();

        List<String> re = list3.stream().map(Person::getName).collect(Collectors.toList());
        log.info("list3:{}", re);
    }

    @Test
    public void listTest() {
        int start = 10;
        int end = 20;
        List<Integer> intList = new ArrayList<>();
        for (int sta = start; sta <= end; sta++) {
            intList.add(sta);
        }

        int removeInt = intList.remove((int) (Math.random() * intList.size()));
        int foundInt = this.found(start, end, intList);
        log.info("删除：{}====查找：{}", removeInt, foundInt);
        log.info("查找删除的元素：{}", removeInt == foundInt ? "成功" : "失败");
    }

//    private int found(int start, int end, List<Integer> num) {
//        for(int s =start; s <= end; s++){
//            if(!num.contains(s)){
//                return s;
//            }
//        }
//        return 0;
//    }

    // 对list求和，原有序数组的和减去list的和就是被删除的数
//    private int found(int start, int end, List<Integer> num) {
//        int sum = 0;
//        for (Integer i : num) {
//            sum += i;
//        }
//        // 高斯求和 (首数+尾数)*项数/2
//        // 项数 = 尾数-首数+1
//        return (start + end) * (end - start + 1) / 2 - sum;
//    }

    private int found(int start, int end, List<Integer> list) {
        boolean[] a = new boolean[end - start + 1];
        for (int value : list) {
            a[value - start] = true;
        }
        for (int i = 0; i < a.length; ++i) {
            if (!a[i]) {
                return (i + start);
            }
        }
        return -1;
    }


    static class Student{
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Student(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student)) return false;
            Student student = (Student) o;
            return age == student.age &&
                    name.equals(student.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

}
