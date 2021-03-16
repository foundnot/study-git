package base.reflex;



public class Person implements Comparable<Person>{

    private String name;

    public int age;

    private String br;

    public String birthDay;

    private boolean status;

    public Detail detail;

    public boolean isStatus() {
        return status;
    }

    public Person() {
    }

    public Person(String name, int age, String birthDay) {
        this.name = name;
        this.age = age;
        this.birthDay = birthDay;
    }

    public Person(String name, int age, String birthDay, boolean status, Detail detail) {
        this.name = name;
        this.age = age;
        this.birthDay = birthDay;
        this.status = status;
        this.detail = detail;
    }


    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name) ;
    }

    static class Detail{
        public String like = "123456";
        private String speciality;
        public long num = 10000;

        public Detail(String like, String speciality, long num) {
            this.like = like;
            this.speciality = speciality;
            this.num = num;
        }

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        @Override
        public String toString() {
            return "Detail{" +
                    "like='" + like + '\'' +
                    ", speciality='" + speciality + '\'' +
                    ", num=" + num +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthDay='" + birthDay + '\'' +
                ", status=" + status +
                ", detail=" + detail +
                '}';
    }
}
