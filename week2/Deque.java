import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node<Item> first, last;

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> temp = new Node<Item>();
        temp.item = item;
        temp.next = first;
        if (first != null) first.prev = temp;
        else last = temp;
        first = temp;
        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> temp = new Node<Item>();
        temp.item = item;
        temp.prev = last;
        if (last != null) last.next = temp;
        else first = temp;
        last = temp;
        n++;
    }

    public Item removeFirst() {
        if (n == 0) throw new NoSuchElementException();
        Node<Item> temp = first;
        first = first.next;
        if (first != null) first.prev = null;
        else last = null;
       n--;
        return temp.item;
    }

    public Item removeLast() {
        if (n == 0) throw new NoSuchElementException();
        Node<Item> temp = last;
        last = last.prev;
        if (last != null) last.next = null;
        else first = null;
        n--;
        return temp.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> cur = first;
        public boolean hasNext() { return cur != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(0);
        System.out.println(String.valueOf(dq.removeFirst()));
        for (int i: dq) {
            System.out.println(i);
        }
    }
}
