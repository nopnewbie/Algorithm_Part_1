import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

import static edu.princeton.cs.algs4.StdRandom.uniform;

/**
 * Created by lw on 2017/3/5.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] mItems;
    private int first;
    private int last;
    private int length;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {                // construct an empty randomized queue
        mItems = (Item[]) new Object[2];
        first = 0;
        last = -1;
    }

    public boolean isEmpty() {               // is the queue empty?
        return last == -1;
    }

    public int size() {                       // return the number of items on the queue
        return last + 1;
    }

    public void enqueue(Item item) {          // add the item
        addValidate(item);
        check_n_copy();
        mItems[++last] = item;
        length = size();
    }

    public Item dequeue() {                   // remove and return a random item
        dequeValidate();
        int index = StdRandom.uniform(first, length);
        swap(index, last);
        Item ret = mItems[last--];
        mItems[last+1] = null;
        length = size();
        check_n_copy();
        return ret;
    }

    public Item sample() {                    // return (but do not remove) a random item
        dequeValidate();
        int index = StdRandom.uniform(first, length);
        return mItems[index];
    }

    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomIterator<Item>(mItems, length);
    }

    private void swap(int x, int y) {
        Item tmp = mItems[x];
        mItems[x] = mItems[y];
        mItems[y] = tmp;
    }

    private void addValidate(Item item) {
        if (null == item) {
            throw new NullPointerException();
        }
    }

    private void dequeValidate() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @SuppressWarnings("unchecked")
    private void check_n_copy() {
        if (last == mItems.length - 1) {
            Item[] items = (Item[]) new Object[2*mItems.length];
            for (int i = 0; i < mItems.length; ++i) {
                items[i] = mItems[i];
            }
            mItems = items;
        } else if (last <= mItems.length / 4) {
            int newSize = (last > -1) ? last + 1 : 1;
            Item[] items = (Item[]) new Object[newSize];
            for (int i = 0; last > -1 && i < newSize; ++i) {
                items[i] = mItems[i];
            }
            mItems = items;
        }
    }


    private static class RandomIterator<Item> implements Iterator<Item> {

        private Item[] mTheItems;
        private int mLength;

        @SuppressWarnings("unchecked")
        public RandomIterator(final Item[] theItems, final int length) {
            mTheItems = (Item[]) new Object[length];
            for (int i = 0; i < length; ++i) {
                mTheItems[i] = theItems[i];
            }
            mLength = length;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return mLength > 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = StdRandom.uniform(0, mLength);
            swap(index, mLength - 1);
            return mTheItems[--mLength];
        }

        private void swap(int x, int y) {
            Item tmp = mTheItems[x];
            mTheItems[x] = mTheItems[y];
            mTheItems[y] = tmp;
        }
    }

    public static void main(String[] args) {  // unit testing (optional)

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("IKRZZAGIVF");
        rq.enqueue("AJBDEHDYSN");
        rq.enqueue("AEGAZAKXBL");
        rq.dequeue();
        rq.enqueue("UWCGEFIRSO");
        rq.enqueue("IYPIEEUVRT");
        rq.enqueue("FTJCUZJKNH");
        rq.enqueue("QTYJTTHXVF");
        rq.enqueue("EOANBRNQND");
        rq.dequeue();
    }

}
