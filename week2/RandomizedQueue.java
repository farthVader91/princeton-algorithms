import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return n;
    }

    public Item sample() {
        if (n == 0) throw new NoSuchElementException();
        int idx = StdRandom.uniform(n);
        Item tmp = q[idx];
        return tmp;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; ++i) {
            temp[i] = q[i];
        }
        q = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        // Resize array if it is completely full.
        // Double the array to provide padding and amortizing cost.
        if (n == q.length) resize(q.length * 2);
        q[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        // Resize array if we are utilising only 1/4th of internal queue size
        // Resize it to half the size to provide some padding and amortize cost.
        if (n <= (q.length / 4)) resize(q.length / 2);
        int hole = StdRandom.uniform(n);
        Item item = q[hole];
        // We will use a trick here: we reassign the element
        // at random index with the last element to fill the gap
        // instead of otherwise shifting each element(which is inefficient).
        // This is fine because we are ultimately dequeueing at random
        // and the original queue order needn't be preserved.
        if (hole != n - 1) q[hole] = q[n - 1];
        q[--n] = null; // to avoid loitering
        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] innerQ;

        public ArrayIterator() {
            // Trick: We will shuffle the internal array in-place
            // and simple iterate over the array from first to last.
            innerQ = q.clone();
            shuffleArray(innerQ);
        }

        private void shuffleArray(Item[] arr) {
            for (int pos = n - 1; pos > 0; pos--) {
                int idx = StdRandom.uniform(pos + 1);
                Item tmp = arr[idx];
                arr[idx] = arr[pos];
                arr[pos] = tmp;
            }
        }

        public boolean hasNext() { return i < n; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = innerQ[i++];
            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        Iterator<Integer> it1, it2;
        it1 = rq.iterator();
        it2 = rq.iterator();
        while (it1.hasNext()) {
            System.out.println(
                               String.valueOf(it1.next()) + "-" +
                               String.valueOf(it2.next())
                               );
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
