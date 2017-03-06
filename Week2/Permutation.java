
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by lw on 2017/3/6.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
//        int k = StdIn.readInt();
//        In in = new In(args[1]);
        String[] strings = StdIn.readAllStrings();
        RandomizedQueue<String> stringRandomizedQueue = new RandomizedQueue<>();
        for (String s : strings) {
            stringRandomizedQueue.enqueue(s);
        }

        for (int i = 0; i < k; ++i) {
            String s = stringRandomizedQueue.dequeue();
            StdOut.println(s);
        }
    }
}
