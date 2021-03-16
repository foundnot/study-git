package base.reflex;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class ReflexDemo {

    @Test
    public void getFieldByReflex(){
        this.reflexField();
    }

    @Test
    public void getMethodByReflex(){
        this.reflexMethod();
    }

    /**
     * Java的反射API提供的Field类封装了字段的所有信息：
     * 通过Class实例的方法可以获取Field实例：getField()，getFields()，getDeclaredField()，getDeclaredFields()；
     * 通过Field实例可以获取字段信息：getName()，getType()，getModifiers()；
     * 通过Field实例可以读取或设置某个对象的字段，如果存在访问限制，要首先调用setAccessible(true)来访问非public字段。
     * 通过反射读写字段是一种非常规方法，它会破坏对象的封装
     * */
    @SneakyThrows
    private void reflexField(){
        Person person = new Person();
        person.setName("Bob");
        person.setAge(20);
        person.setBirthDay("01-01");

        Map<String, String> PersonMap = BeanToMap.toMap(person);
//        Map<String, String> PersonMap = BeanToMap.toMap2(person);
        log.info("map:{}", PersonMap);
        Class<? extends Person> personClass = person.getClass();
        log.info("getFields:{}", Arrays.toString(personClass.getFields()));
        log.info("getField1:{}", personClass.getDeclaredField("name").getModifiers());
        log.info("getField2:{}", Modifier.isPrivate(personClass.getField("age").getModifiers()));
        Field age = personClass.getField("age");
        log.info("getPublicValue:{}", age.get(person));
        Field name = personClass.getDeclaredField("name");
        name.setAccessible(true); // 允许获取非public修饰字段的值
        log.info("getValue:{}", name.get(person));

        age.set(person, 18);
        log.info("getSetPublicValue:{}", age.get(person));

        name.set(person, "hahah");
        log.info("getSetValue:{}", name.get(person));

    }


    /**
     * 通过Class实例的方法可以获取Method实例：getMethod()，getMethods()，getDeclaredMethod()，getDeclaredMethods()；
     * 通过Method实例可以获取方法信息：getName()，getReturnType()，getParameterTypes()，getModifiers()；
     * 通过Method实例可以调用某个对象的方法：Object invoke(Object instance, Object... parameters)；
     * 通过设置setAccessible(true)来访问非public方法；
     * 通过反射调用方法时，仍然遵循多态原则。
     */
    @SneakyThrows
    private void reflexMethod(){
        Person person = new Person();
        Class<? extends Person> personClass = person.getClass();
        Method setNameMethod = personClass.getMethod("setName", String.class);
        log.info("setNameMethod:{}", setNameMethod);
        log.info("getParameterCount:{}", setNameMethod.getParameterCount());
        Object invoke = setNameMethod.invoke(person, "setNameMethod");
        log.info(" request method response:{}", person.getName());

        Method hello = personClass.getDeclaredMethod("toString");
        hello.setAccessible(true);
        log.info(" request method response:{}", hello.invoke(person, null));

        // 无参构造
        Person person1 = Person.class.newInstance();
        // 带参构造方法
        Constructor<Person> constructor1 = Person.class.getConstructor(String.class, int.class, String.class, boolean.class, Person.Detail.class);
        Person person3 = constructor1.newInstance("bob", 20, "01-01", false, new Person.Detail("eat", "sleep", 100));
        log.info("person3:{}", person3.toString());

        Constructor<? extends Person> constructor = personClass.getConstructor(String.class, int.class, String.class);
        Person person2 = constructor.newInstance("hahh", 18, "00-01");
        log.info("name:{}===age:{}===birth:{}", person2.getName(), person2.getAge(), person2.getBirthDay());

        // 父类名称
        Class<?> superclass = personClass.getSuperclass();
        log.info("superClass:{}", superclass.getName());


    }


}
