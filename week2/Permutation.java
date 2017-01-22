import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    private int k;
    public RandomizedQueue<String> rq;

    public Permutation(int count) {
        k = count;
        rq = new RandomizedQueue<String>();
    }

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        Permutation p = new Permutation(k);
        for(int i = 0; i < k; i++) {
            p.rq.enqueue(StdIn.readString());
        }
        for(String s: p.rq) {
            System.out.println(s);
        }
    }
}
