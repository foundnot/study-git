package base.collection;

import org.junit.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueDemo {

    /*队列Queue实现了一个先进先出（FIFO）的数据结构：
       通过add()/offer()方法将元素添加到队尾；
       通过remove()/poll()从队首获取元素并删除；
       通过element()/peek()从队首获取元素但不删除。
       要避免把null添加到队列。
  */
    @Test
    public void testQueue(){
        Queue<String> queueList = new LinkedList<>();
        queueList.offer("aa");
        queueList.add("bb");

    }

    /*
      PriorityQueue实现了一个优先队列：从队首获取元素时，总是获取优先级最高的元素。
      PriorityQueue默认按元素比较的顺序排序（必须实现Comparable接口），也可以通过Comparator自定义排序算法（元素就不必实现Comparable接口）
    */
    @Test
    public void PriorityQueue(){
        Queue<User> q = new PriorityQueue<>(new UserComparator());
//        Queue<User> q = new PriorityQueue<>();
        // 添加3个元素到队列:
        q.offer(new User("Bob", "A1"));
        q.offer(new User("Alice", "A2"));
        q.offer(new User("Boss", "V1"));
        System.out.println(q.poll()); // Boss/V1
        System.out.println(q.poll()); // Bob/A1
        System.out.println(q.poll()); // Alice/A2
        System.out.println(q.poll()); // null,因为队列为空
    }


    static class UserComparator implements Comparator<User> {
        public int compare(User u1, User u2) {
            if (u1.number.charAt(0) == u2.number.charAt(0)) {
                // 如果两人的号都是A开头或者都是V开头,比较号的大小:
                return u1.number.compareTo(u2.number);
            }
            if (u1.number.charAt(0) == 'V') {
                // u1的号码是V开头,优先级高:
                return -1;
            } else {
                return 1;
            }
        }
    }

    static class User implements Comparable<User>{
        private String name;
        private String number;

        public User() {
        }

        public User(String name, String number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", number='" + number + '\'' +
                    '}';
        }

        @Override
        public int compareTo(User u) {
            if (this.number.charAt(0) == u.number.charAt(0)) {
                return Integer.parseInt(this.number.substring(1)) > Integer.parseInt(u.number.substring(1)) ? 1 : -1;
            }
            if (this.number.charAt(0) == 'V') {
                return -1;
            } else {
                return 1;
            }
        }
    }

}
