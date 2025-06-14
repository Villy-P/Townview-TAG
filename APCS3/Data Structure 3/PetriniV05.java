package Lab5;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PetriniV05 {
    public static void main(String[] args) throws IOException {
        final PetriniList<String> list=  new PetriniList<>();
        final Scanner s = new Scanner(new File("UnorderedListOfAnimals.txt"));
        while (s.hasNextLine()) {
            String next = s.nextLine();
            String[] split = next.split("\\s+");
            if (split[0].equals("Add"))
                list.add(split[1]);
            if (split[0].equals("Print"))
                list.print();
            if (split[0].equals("Has"))
                System.out.println(list.contains(split[1]) ? "The list has " + split[1] : split[1] + " is not in the list.");
            if (split[0].equals("Remove"))
                list.remove(split.length == 1 ? 0 : Integer.parseInt(split[1]));
        }
    }
}

class PetriniList<E extends Comparable<E>> {
    private PetriniNode<E> head;

    public void add(E e) {
        final PetriniNode<E> node = new PetriniNode<>(e);
        if (this.head == null) {
            this.head = node;
            return;
        }
        PetriniNode<E> current = this.head;
        while (current.getNext() != null)
            current = current.getNext(); 
        current.setNext(node);  
    }

    public void print() {
        System.out.print("[ ");
        PetriniNode<E> current = this.head;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            current = current.getNext();
        }
        System.out.println(']');
    }

    public boolean contains(E item) {
        PetriniNode<E> current = this.head;
        while (current != null) {
            if (current.getValue().equals(item))
                return true;
            current = current.getNext();
        }
        return false;
    }

    public void remove(int index) {
        if (index == 0) {
            System.out.println("Remove: " + this.head.getValue());
            this.head = this.head.getNext();
            return;
        }
        PetriniNode<E> prev = this.head;
        PetriniNode<E> current = this.head;
        while (index != 0) {
            prev = current;
            current = current.getNext();
            index--;
        }
        prev.setNext(current.getNext());
        System.out.println("Remove: " + current.getValue());
    }
}

class PetriniNode<E extends Comparable<E>> {
    private E value;
    private PetriniNode<E> next;

    public PetriniNode(E e) {
        this.value = e;
    }

    public PetriniNode<E> getNext() {
        return this.next;
    }
    public E getValue() {
        return this.value;
    }

    public void setNext(PetriniNode<E> next) {
        this.next = next;
    }
}
