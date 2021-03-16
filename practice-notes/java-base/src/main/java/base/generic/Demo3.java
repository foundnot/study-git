package base.generic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo3 {

    public static void main(String[] args) {
        Person p = new Student();
        Student student = new Student();
//        paramInterface(p);
//        paramInterface(student);
//        paramClass(student);
//        paramClass(p);   // error  Type mismatch

        PrimaryStudent primaryStudent = new PrimaryStudent();
//        paramClass(primaryStudent);
//        paramSonClass(primaryStudent);
//        paramSonClass(student);  // error Type mismatch


        p = returnInterface();
//        student = returnInterface();  // error
        student = returnStudent();
        p = returnStudent();

        student = returnPrimaryStudent();

    }


    public static Person returnInterface(){
        log.info("returnInterface:{}", "returnInterface");
        return (category, num) -> null;
    }

    public static Student returnStudent(){
        log.info("returnStudent:{}", "returnStudent");
        return new Student();
    }

    public static PrimaryStudent returnPrimaryStudent(){
        log.info("returnPrimaryStudent:{}", "returnPrimaryStudent");
        return new PrimaryStudent();
    }

    public static void paramInterface(Person person){
        log.info("interfaceParam:{}", person.eat("aa", 10));
    }

    public static void paramClass(Student student){
        log.info("paramClass:{}", student.eat("bb", 10));
    }

    public static void paramSonClass(PrimaryStudent student){
        log.info("paramSonClass:{}", student.attendClass("classRoom", 45));
    }

}
