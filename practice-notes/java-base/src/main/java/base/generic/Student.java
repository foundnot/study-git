package base.generic;


public class Student implements Person {

    @Override
    public String eat(String category, int num) {
        return category + "-eat-" + num;
    }

    public String attendClass(String place, int duration){
        return "student at -" + place + "-" + duration;
    }

}
