import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedListStack<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    /**
     * Having the Node class as static makes it more of a top-level
     * class that is not bound to an instance of the outer class.
     * Hence other classes can create instances of Node without
     * creating any instance of LinkedListStack.
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public LinkedListStack() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item popped = first.item;
        first = first.next;
        n--;
        return popped;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    public String toString() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new LinkedListIterator(first);
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        LinkedListStack<String> stack = new LinkedListStack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            } else if (!stack.isEmpty()) {
                StdOut.print(stack.pop() + ' ');
            }
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
