import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Lab7.PetriniNode;
import Lab7.PetriniTree;

public class PetriniTree<E extends Comparable<E>> {
    private PetriniNode<E> root;

    public static void main(String[] args) throws IOException {
        final PetriniTree<String> list=  new PetriniTree<>();
        final Scanner s = new Scanner(new File("TreeAnimals.txt"));
        while (s.hasNextLine()) {
            String next = s.nextLine();
            list.add(next);
        }
        System.out.println(list);
    }

    public void add(E value) {
        PetriniNode<E> temp = new PetriniNode<E>(value);
        if (this.root == null) {
            this.root = temp;
            return;
        }
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

    public String toString() {
        return "Prefix:      [ " + this.preorder(this.root) + "]\n" +
               "Infix:       [ " + this.inorder(this.root) + "]\n" + 
               "Postfix      [ " + this.postOrder(this.root) + "]\n";
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
}