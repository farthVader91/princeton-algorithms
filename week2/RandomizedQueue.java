import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N;

    public RandomizedQueue() {
        q = (Item[])new Object[2];
    }

    public boolean isEmpty() {
        return (N == 0);
    }

    public int size() {
        return N;
    }



    private void resize(int capacity) {
        assert(capacity >= N);
        Item[] temp = (Item[])new Object[capacity];
        for(int i = 0; i < N; ++i) {
            temp[i] = q[i];
        }
        q = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        // Resize array if it is completely full.
        // Double the array to provide padding and amortizing cost.
        if (N == q.length) resize(q.length * 2);
        q[N++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        // Resize array if we are utilising only 1/4th of internal queue size
        // Resize it to half the size to provide some padding and amortize cost.
        if(N <= (q.length / 4)) resize(q.length / 2);
        int hole = StdRandom.uniform(N);
        Item item = q[hole];
        // We will use a trick here: we reassign the element
        // at random index with the last element to fill the gap
        // instead of otherwise shifting each element(which is inefficient).
        // This is fine because we are ultimately dequeueing at random
        // and the original queue order needn't be preserved.
        if(hole != N - 1) q[hole] = q[N - 1];
        q[--N] = null; // to avoid loitering
        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        /*
        public ArrayIterator() {
            super();
            // Trick: We will shuffle the internal array in-place
            // and simple iterate over the array from first to last.
            shuffleArray(q);
        }
        */

        private void shuffleArray(Item[] arr) {
            for(int i = N - 1; i > 0; i--) {
                int idx = StdRandom.uniform(i + 1);
                Item tmp = arr[idx];
                arr[idx] = arr[i];
                arr[i] = tmp;
            }
        }

        public boolean hasNext() { return i < N; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (i == 0) shuffleArray(q);
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[i++];
            return item;
        }
    }


    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(5);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(6);
        rq.enqueue(1);
        rq.enqueue(9);
        System.out.println(rq.dequeue());
        for(int i: rq) {
            System.out.println(String.valueOf(i));
        }
        /*
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        */
    }
}
