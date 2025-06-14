import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PetriniV17 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("twoEndedCandle.txt"));
        PetriniStructure<String> e = new PetriniStructure<String>();
        while (s.hasNextLine()) {
            String next = s.nextLine();
            if (next.equals("string"))
                e.remove();
            else if (next.equals("number"))
                e.removeLast();
            else if (isNumeric(next))
                e.addLast(next);
            else
                e.addFirst(next);
            System.out.println(e);
        }
        s.close();
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray())
            if (!Character.isDigit(c) && c != '-') 
                return false;
        return true;
    }
}

class PetriniStructure<E> {
    private PetriniNode<E> head;
    private PetriniNode<E> tail;

    public void add(E e) {
        PetriniNode<E> toAdd = new PetriniNode<E>(e);
        if (this.tail == null) {
            this.tail = toAdd;
            PetriniNode<E> current = this.head;
            while (current.getNext() != null)
                current = current.getNext();
            current.setNext(toAdd);
            toAdd.setPrevious(current);
            return;
        }
        PetriniNode<E> temp = this.tail;
        temp.setNext(toAdd);
        this.tail = toAdd;
        this.tail.setPrevious(temp);
    }

    public void addLast(E e) {
        this.add(e);
    }

    public void addFirst(E e) {
        PetriniNode<E> toAdd = new PetriniNode<E>(e);
        if (this.head == null) {
            this.head = toAdd;
            return;
        }
        PetriniNode<E> temp = this.head;
        head.setPrevious(toAdd);
        this.head = toAdd;
        this.head.setNext(temp);
    }

    public void remove() {
        System.out.println("\tString " + this.head);
        this.head.getNext().setPrevious(null);
        this.head = this.head.getNext();
    }

    public void removeLast() {
        System.out.println("\tNumber " + this.tail);
        this.tail.getPrevious().setNext(null);
        this.tail = this.tail.getPrevious();
    }

    public void removeFirst() {
        this.remove();
    }

    @Override
    public String toString() {
        PetriniNode<E> current = this.head;
        String ret = "";
        while (current != null) {
            ret += current + " ";
            current = current.getNext();
        }
        return ret;
    }
}

class PetriniNode<E> {
    private E value;
    private PetriniNode<E> previous;
    private PetriniNode<E> next;

    public PetriniNode(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    public PetriniNode<E> getPrevious() {
        return this.previous;
    }

    public void setPrevious(PetriniNode<E> previous) {
        this.previous = previous;
    }

    public PetriniNode<E> getNext() {
        return this.next;
    }

    public void setNext(PetriniNode<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}