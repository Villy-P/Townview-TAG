import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PetriniV13 {
    public static void main(String[] args) {        
        PetriniTree<Integer> tree = new PetriniTree<Integer>();
        for (int i = 0; i < 30; i++)
            tree.add(randomNumber(1, 10));
        System.out.println(tree.root);
    }

    public static int randomNumber(double min, double max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
}

class PetriniTree<E extends Comparable<E>> {
    public PetriniNode<E> root;

    public static void main(String[] args) throws IOException {
        final PetriniTree<String> list=  new PetriniTree<>();
        final Scanner s = new Scanner(new File("TreeAnimals.txt"));
        while (s.hasNextLine()) {
            String next = s.nextLine();
            list.add(next);
        }
        System.out.println(list);
        s.close();
    }

    public boolean contains(E value) {
        return this.root.contains(value);
    }

    public boolean add(E value) {
        PetriniNode<E> temp = new PetriniNode<E>(value);
        if (this.root == null) {
            this.root = temp;
            return true;
        }
        if (this.root.contains(value))
            return false;
        PetriniNode<E> current = this.root;
        PetriniNode<E> prev = null;
        while (current != null) {
            prev = current;
            if (current.getValue().compareTo(value) < 0)
                current = current.getRight();
            else
                current = current.getLeft();
        }
        if (prev.getValue().compareTo(value) < 0)
            prev.setRight(temp);
        else
            prev.setLeft(temp);
        return true;
    }

    public String preorder(PetriniNode<E> temp) {
        if (temp == null)
            return "";
        String cur = "";
        cur += temp.getValue() + " ";
        cur += this.preorder(temp.getLeft());
        cur += this.preorder(temp.getRight());
        return cur;
    }

    public String inorder(PetriniNode<E> temp) {
        if (temp == null)
            return "";
        String cur = "";
        cur += this.inorder(temp.getLeft());
        cur += temp.getValue() + " ";
        cur += this.inorder(temp.getRight());
        return cur;
    }

    public String postOrder(PetriniNode<E> temp) {
        if (temp == null)
            return "";
        String cur = "";
        cur += this.postOrder(temp.getLeft());
        cur += this.postOrder(temp.getRight());
        cur += temp.getValue() + " ";
        return cur;
    }
}

class PetriniNode<E extends Comparable<E>> {
    private E value;
    private PetriniNode<E> left;
    private PetriniNode<E> right;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public PetriniNode<E> getLeft() {
        return left;
    }

    public void setLeft(PetriniNode<E> left) {
        this.left = left;
    }

    public PetriniNode<E> getRight() {
        return right;
    }

    public void setRight(PetriniNode<E> right) {
        this.right = right;
    }

    public PetriniNode(E e) {
        this.value = e;
    }

    public boolean contains(E value) {
        Queue<PetriniNode<E>> q = new LinkedList<>();
        q.add(this);
        while (q.size() > 0) {
            PetriniNode<E> n = q.remove();
            if (n.left != null)
                q.add(n.left);
            if (n.right != null)
                q.add(n.right);
            if (n.value.equals(value))
                return true;
        }
        return false;
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
            s += " " + n.value + " ";
        }
        return s;
    }
}