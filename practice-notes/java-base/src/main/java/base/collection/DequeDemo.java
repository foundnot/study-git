package base.collection;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

@Slf4j
public class DequeDemo {

    /*
    Deque 两头都进，两头都出，这种队列叫双端队列（Double Ended Queue）
    既可以添加到队尾，也可以添加到队首；
    既可以从队首获取，又可以从队尾获取。

    将元素添加到队尾或队首：addLast()/offerLast()/addFirst()/offerFirst()；
    从队首／队尾获取元素并删除：removeFirst()/pollFirst()/removeLast()/pollLast()；
    从队首／队尾获取元素但不删除：getFirst()/peekFirst()/getLast()/peekLast()；
    总是调用xxxFirst()/xxxLast()以便与Queue的方法区分开；
    避免把null添加到队列

    栈（Stack）是一种后进先出（LIFO：Last In First Out）的数据结构
    栈（Stack）是一种后进先出（LIFO）的数据结构，操作栈的元素的方法有：
    把元素压栈：push(E)；
    把栈顶的元素“弹出”：pop(E)；
    取栈顶元素但不弹出：peek(E)。
    在Java中，我们用Deque可以实现Stack的功能，注意只调用push()/pop()/peek()方法，避免调用Deque的其他方法。
    最后，不要使用遗留类Stack。
    */

    // 把一个给定的整数转换为十六进制
    @Test
    public void test1() {
        String hex = toHex(12500);
        log.info("hex:{}", hex);
        if (hex.equalsIgnoreCase("30D4")) {
            System.out.println("测试通过");
        } else {
            System.out.println("测试失败");
        }
    }

    static String toHex(int n) {
        Deque<String> deque = new LinkedList<>();
        while (n != 0) {
            deque.push(Integer.toHexString(n % 16));
            n = n / 16;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : deque) {
            sb.append(s);
        }
        return sb.toString();
    }


}
