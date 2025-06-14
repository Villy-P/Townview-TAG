import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PetriniV12 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("DogPile.txt"));
        PetriniDogPile<String> pile = new PetriniDogPile<String>();
        ArrayList<String> dogs = new ArrayList<>();
        while (s.hasNextLine())
            dogs.add(s.nextLine().toLowerCase());
        int nums = randomNumber(5, 10);
        for (int i = 0; i < nums; i++) {
            int num = randomNumber(0, dogs.size() - 1);
            pile.add(dogs.get(num));
            dogs.remove(num);
        }
        System.out.println(pile);
        s.close();
    }

    public static int randomNumber(double min, double max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
}

class PetriniDogPile<E extends Comparable<E>> {
    public PetriniNode<E> root;

    public void add(E e) {
        if (this.root == null)
            this.root = new PetriniNode<E>(e);
        else
            this.root.add(e);
        this.sort();
    }

    public void sort() {
        root.sort();
    }

    public String toString() {
        return root.toString();
    }
}

class PetriniNode<E extends Comparable<E>> {
    public E value;
    public PetriniNode<E> left;
    public PetriniNode<E> right;

    public PetriniNode(E e) {
        this.value = e;
    }

    public void add(E e) {
        if (this.left != null) {
            left.add(e);
            return;
        }
        if (this.right != null) {
            right.add(e);
            return;
        }
        this.left = new PetriniNode<E>(e);
    }

    public void sort() {
        if (this.left == null)
            return;
        this.left.sort();
        if (this.left.value.compareTo(this.value) < 0) {
            E value = this.value;
            this.value = this.left.value;
            this.left.value = value;
        }
    }

    public String toString() {
        String s = "";
        Queue<PetriniNode<E>> q = new LinkedList<>();
        q.add(this);
        while (q.size() > 0) {
            PetriniNode<E> n = q.remove();
            if (n.left != null)
                q.add(n.left);
            if (n.right != null)
                q.add(n.right);
            s += "[ " + n.value + " ]";
        }
        return s;
    }
}

/*
 * if value is null value = param
if left is null left = Node(param)
if right " right "
Queue<PN> q = new LinkedList<PN>();
q.add(this)
while (q.size() > 0)
    PN n = q.remove();
    if (n.left is null)
        n.right();
    q.add(n.left);
    q.add(n.right);
 */