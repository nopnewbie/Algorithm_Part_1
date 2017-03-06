import edu.princeton.cs.algs4.In;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by lw on 2017/3/5.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head;
    private Node<Item> last;
    private int length;

    public Deque() {
        // construct an empty deque
        head = new Node<Item>();
        last = head;
        length = 0;
    }

    public boolean isEmpty() {
        // is the deque empty?
        return length == 0;
    }

    public int size() {                       // return the number of items on the deque
        return length;
    }

    public void addFirst(Item item) {         // add the item to the front
        addValidate(item);
        Node<Item> node = new Node<Item>(item);
        node.next = head.next;
        if (null != head.next) {
            head.next.prev = node;
        } else {
            last = node;
        }
        node.prev = head;
        head.next = node;
        ++length;
    }

    public void addLast(Item item) {         // add the item to the end
        addValidate(item);
        Node<Item> node = new Node<Item>(item);
        last.next = node;
        node.prev = last;
        last = node;
        ++length;
    }

    @SuppressWarnings("unchecked")
    public Item removeFirst() {               // remove and return the item from the front
        removeValidate();
        Node<Item> node = head.next;
        head.next = node.next;
        if (null != node.next) {
            node.next.prev = head;
        } else {

            last = node.prev;
        }
        --length;
        return node.data;

    }

    @SuppressWarnings("unchecked")
    public Item removeLast() {                // remove and return the item from the end
        removeValidate();
        Node<Item> node = last;
        last = node.prev;
        last.next = node.next;
        --length;
        return node.data;
    }

    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
        return new Iterator<Item>() {
            private Node<Item> current = head;

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {

                return current.next != null;
            }

            @SuppressWarnings("unchecked")
            @Override
            public Item next() {
                current = current.next;
                if (null == current) {
                    throw new NoSuchElementException();
                }
                return current.data;
            }
        };
    }

    private void addValidate(Item item) {
        if (null == item) {
            throw new NullPointerException();
        }
    }

    private void removeValidate() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private static class Node<Item> {

        Node() {
            data = null;
            next = null;
            prev = null;
        }

        Node(Item item) {
            data = item;
            next = null;
            prev = null;
        }
        Item data;
        Node<Item> next;
        Node<Item> prev;
    }

    public static void main(String[] args) {  // unit testing (optional)
        Deque<Double> deque = new Deque<Double>();
        In in = new In(args[0]);
        double[] doubles = in.readAllDoubles();
        Iterator<Double> iterator = deque.iterator();
        iterator.next();
    }
}
