package base.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
public class IteratorDemo {

  /*
    Iterator是一种抽象的数据访问模型。使用Iterator模式进行迭代的好处有：
    对任何集合都采用同一种访问模型；
    调用者对集合内部结构一无所知；
    集合类返回的Iterator对象知道如何迭代。
    Java提供了标准的迭代器模型，即集合类实现java.util.Iterable接口，返回java.util.Iterator实例
  */

    @Test
    public void test(){
        ReverseList<String> rlist = new ReverseList<>();
        rlist.add("Apple");
        rlist.add("Orange");
        rlist.add("Pear");

        Iterator<String> iterator = rlist.iterator();
//        for (; iterator.hasNext(); ) {
//            log.info("==:{}",iterator.next());
//
//        }
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }

    static class ReverseList<T> implements Iterable<T> {
        private List<T> list = new ArrayList<>();
        public void add(T t) {
            list.add(t);
        }

        @Override
        public Iterator<T> iterator() {
            log.info("===iterator====");
            return new ReverseIterator(list.size());
        }

        class ReverseIterator implements Iterator<T> {
            int index;
            ReverseIterator(int index) {
                this.index = index;
            }

            @Override
            public boolean hasNext() {
                log.info("===hasNext====");
                return index > 0;
            }

            @Override
            public T next() {
                index--;
                log.info("===next====");
                return ReverseList.this.list.get(index);
            }
        }
    }

}
