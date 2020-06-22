import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code RandomizedQueue} class is similar to a stack or queue,
 * except that the item removed is chosen uniformly at random among
 * items in the data structure.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] queue;

    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Input cannot be null");
        if (size() == queue.length) resize(queue.length * 2);
        queue[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int i = StdRandom.uniform(n);
        Item item = queue[i];
        queue[i] = queue[n - 1];
        queue[n - 1] = null; // Avoid loitering
        if (size() > 0 && size() == queue.length / 4) resize(queue.length / 2);
        n--;
        return item;
    }

    // returns a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int i = StdRandom.uniform(n);
        return queue[i];
    }

    private void resize(int capacity) {
        assert capacity > 0;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] random;
        private int current;

        public RandomizedQueueIterator() {
            random = new int[n];
            for (int i = 0; i < n; i++) {
                random[i] = i;
            }
            StdRandom.shuffle(random);
            current = 0;
        }

        public boolean hasNext() {
            return current < random.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Queue underflow");
            return queue[random[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else StdOut.print(queue.dequeue() + ' ');
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }
}
