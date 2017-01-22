import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    private RandomizedQueue<String> rq;

    public Permutation() {
        rq = new RandomizedQueue<String>();
    }

    private void add(String s) {
        rq.enqueue(s);
    }

    private String pop() {
        return rq.dequeue();
    }

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        Permutation p = new Permutation();
        for(int i = 0; i < k; i++) {
            p.add(StdIn.readString());
        }
        while (true) {
            try {
                System.out.println(p.pop());
            } catch (NoSuchElementException nse) {
                break;
            }
        }
    }
}
