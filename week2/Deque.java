import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;
    private int first;
    private int last;

    public Deque() {
        q = (Item[])new Object[2];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int capacity) {
	assert(capacity >= n);
        Item[] temp = (Item[])new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
	q = temp;
	first = 0;
	last = n - 1;
    }

    public int size() {
	return n;
    }

    public void addFirst(Item item) {
	if (item == null) throw new NullPointerException();
	if (n == q.length) resize(2 * q.length); // double array-size if necessary
	if (first == 0) {
	    first = q.length; // wrap-around from behind
	}
        q[--first] = item; // add item
	n++;
    }

    public void addLast(Item item) {
	if (item == null) throw new NullPointerException();
	if (n == q.length) resize(2 * q.length); // double array-size if necessary
        last = (last + 1) % q.length;
	q[last] = item; // add item
	n++;
    }

    public Item removeFirst() {
	if (n == 0) throw new NoSuchElementException();
	if (n <= (q.length / 4)) resize(q.length / 2);
	Item item = q[first];
	q[first] = null; // to avoid loitering
        first = (first + 1) % q.length;
	n--;
	return item;
    }

    public Item removeLast() {
	if (n == 0) throw new NoSuchElementException();
	if (n <= (q.length / 4)) resize(q.length / 2);
	Item item = q[last];
	q[last] = null; // to avoid loitering
	if (last == 0) {
	    last = q.length;
	}
        last--;
	n--;
	return item;
    }

    public Iterator<Item> iterator() {
	return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
	private int i = 0;
	public boolean hasNext() { return i < n; }
	public void remove() { throw new UnsupportedOperationException(); }

	public Item next() {
	    if (!hasNext()) throw new NoSuchElementException();
	    Item item = q[(i + first) % q.length];
	    i++;
	    return item;
	}
    }

    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addFirst(3);
        dq.addLast(4);
        dq.addLast(5);
        dq.addLast(6);
        System.out.println(String.valueOf(dq.removeFirst()));
        System.out.println(String.valueOf(dq.removeLast()));
	for(int i: dq) {
	    System.out.println(i);
	}
    }
}
