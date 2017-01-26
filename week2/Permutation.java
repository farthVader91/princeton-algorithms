import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        String[] input = StdIn.readAllStrings();
        for (int i = 0; i < k; i++) {
            rq.enqueue(input[i]);
        }
        while (true) {
            try {
                System.out.println(rq.dequeue());
            } catch (NoSuchElementException nse) {
                break;
            }
        }
    }
}
