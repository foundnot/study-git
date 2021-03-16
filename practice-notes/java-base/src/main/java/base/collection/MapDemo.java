package base.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.*;

/*
HashMap是一种通过对key计算hashCode()，通过空间换时间的方式，直接定位到value所在的内部数组的索引，因此，查找效率非常高
正确使用Map必须保证：
    作为key的对象必须正确覆写equals()方法，相等的两个key实例调用equals()必须返回true；
    作为key的对象还必须正确覆写hashCode()方法，且hashCode()方法要严格遵循以下规范：
        如果两个对象相等，则两个对象的hashCode()必须相等；
        如果两个对象不相等，则两个对象的hashCode()尽量不要相等。

    即对应两个实例a和b：
        如果a和b相等，那么a.equals(b)一定为true，则a.hashCode()必须等于b.hashCode()；
        如果a和b不相等，那么a.equals(b)一定为false，则a.hashCode()和b.hashCode()尽量不要相等。


为何需要重写hashCode和equals
    hashMap中依据key的hash值来确定value存储位置，所以一定要重写hashCode方法，而重写equals方法，
    是为了解决hash冲突，如果两个key的hash值相同，就会调用equals方法，比较key值是否相同，在存储时：
    如果equals结果相同就覆盖更新value值，如果不同就用List他们都存储起来。在取出来是：如果equals结果相同就返回当前value值，
    如果不同就遍历List中下一个元素。即要key与hash同时匹配才会认为是同一个key。
    JDK中源码:if(e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))){ops;}

*/

@Slf4j
public class MapDemo {

    /*
    枚举类不存在重复，应该就不存在相同的 key 值指向不同的 value，不同的 key 指向相同的 value
        如果Map的key是enum类型，推荐使用EnumMap，既保证速度，也不浪费空间。
        使用EnumMap的时候，根据面向抽象编程的原则，应持有Map接口
    */

    @Test
    public void testEnumMap() {
        Map<DayOfWeek, String> map = new EnumMap<>(DayOfWeek.class);
        map.put(DayOfWeek.MONDAY, "星期一");
        map.put(DayOfWeek.TUESDAY, "星期二");
        map.put(DayOfWeek.WEDNESDAY, "星期三");
        map.put(DayOfWeek.THURSDAY, "星期四");
        map.put(DayOfWeek.FRIDAY, "星期五");
        map.put(DayOfWeek.SATURDAY, "星期六");
        map.put(DayOfWeek.SUNDAY, "星期日");
        log.info("map:{}", map);
    }

    @Test
    public void testNoEqualsNoHash() {

        /**
         * 两个key应该是内容相同，但不一定是同一个对象（equals相同才是一个对象）
         *  当map中key是一个没有重写equals hashCode实体类
         *  即时内容相同 也不会被认为是同一个实体类对象 所以在map中取不到value值
         * */

        // HashMap是一种以空间换时间的映射表
        Map<Student, String> studentMap = new HashMap<>(10);
        Student aa = new Student("aa", 10);
        Student aa1 = new Student("aa", 10);
        Student bb = new Student("bb", 30);
        studentMap.put(aa, "aa");
        studentMap.put(aa1, "aa");
        studentMap.put(bb, "bb");

        log.info("aa:{}", studentMap.get(aa));  // aa
        log.info("aa1:{}", studentMap.get(aa1));  // aa
        log.info("bb:{}", studentMap.get(bb)); // bb
        log.info("aaa:{}", studentMap.get(new Student("aa", 10))); //null
        log.info("aaa:{}", studentMap.get(new Student("aa", 20))); //null
        log.info("bbb:{}", studentMap.get(new Student("bb", 30))); //null

        log.info(String.valueOf(aa.equals(aa1))); //false
        log.info(String.valueOf(aa.equals(new Student("aa", 10)))); //false
    }

    @Test
    public void test() {
        Map<EqualsStudent, String> equalsStudentMap = new HashMap<>(10);
        EqualsStudent aa = new EqualsStudent("aa", 10);
        EqualsStudent aa1 = new EqualsStudent("aa", 10);
        EqualsStudent bb = new EqualsStudent("bb", 30);
        equalsStudentMap.put(aa, "aa");
        equalsStudentMap.put(aa1, "aa");
        equalsStudentMap.put(bb, "bb");

        log.info("aa:{}", equalsStudentMap.get(aa));    // aa
        log.info("aa1:{}", equalsStudentMap.get(aa1));  // aa
        log.info("bb:{}", equalsStudentMap.get(bb));    // bb
        log.info("aaa:{}", equalsStudentMap.get(new EqualsStudent("aa", 10))); // aa
        log.info("aaa:{}", equalsStudentMap.get(new EqualsStudent("aa", 10))); // aa
        log.info("bbb:{}", equalsStudentMap.get(new EqualsStudent("bb", 30))); // bb

        log.info(String.valueOf(aa.equals(aa1))); // true
        log.info(String.valueOf(aa.equals(new EqualsStudent("aa", 10))));      // true  重写equals判断是相同的一个对象


    }

    @Test
    public void testTreeMap() {
        // 使用TreeMap时，放入的Key必须实现Comparable接口
        Map<Student, String> map = new TreeMap<>(Comparator.comparing(Student::getName));
        map.put(new Student("dd", 10), "D");
        map.put(new Student("bb", 20), "B");
        map.put(new Student("aa", 30), "A");
        map.forEach((k, v) -> log.info("{}==={}", k, v));
        log.info(":{}", map.get(new Student("bb", 60)));
    }

    @Test
    public void testCompute() {
        // 统计字符出现的次数
        String str = "how are you hello world";
        Map<Character, Integer> computeMap = new HashMap<>();
        Map<String, Integer> computeIfPresentMap = new HashMap<>();
        computeIfPresentMap.put("a", 0);
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            // compute 统计每个字符出现的次数
            computeMap.compute(curChar, (k, v) -> {
                if (v == null) {
                    v = 1;
                } else {
                    v += 1;
                }
                return v;
            });
            // computeIfPresent 统计某一个字符出现的次数
            computeIfPresentMap.computeIfPresent(String.valueOf(curChar), (k, v) -> v + 1);
        }
        log.info("computeMap:{}", computeMap);
        log.info("computeIfPresentMap:{}", computeIfPresentMap);
    }

    @Test
    public void testComputeIfAbsent() {

        List<Student> students = new ArrayList<>();
        students.add(new Student("张三", "男", 18));
        students.add(new Student("李四", "男", 20));
        students.add(new Student("韩梅梅", "女", 18));
        students.add(new Student("小红", "女", 45));

        //按条件分组
        Map<String, List<Student>> groupMap = new HashMap<>();
        for (Student student : students) {
            List<Student> s = groupMap.computeIfAbsent(student.getSex(), v -> new ArrayList<>());
            s.add(student);
        }
        log.info("groupMap:{}", groupMap);

        // 按条件统计
        Map<String, Integer> resultMap = new HashMap<>();
        for (Student student : students) {
            resultMap.merge(student.getSex(), student.getAge(), Integer::sum);
        }
        log.info("resultMap:{}", resultMap);

    }

    static class Student {
        private String name;
        private String sex;
        private int age;

        public Student(String name, String sex, int age) {
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    static class EqualsStudent {

        private String name;
        private int age;

        public EqualsStudent(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EqualsStudent)) return false;
            EqualsStudent that = (EqualsStudent) o;
            return age == that.age &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }


}
