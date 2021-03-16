package spring.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-bean.xml");
        Person person = (Person) applicationContext.getBean("person");
        Person person2 = (Person) applicationContext.getBean("person");
        System.out.println("person:" + person.toString());
        System.out.println("scope:" + (person==person2));
    }

}
