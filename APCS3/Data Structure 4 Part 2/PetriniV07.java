import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PetriniV07 {
    public static void main(String[] args) throws IOException {
        final PetriniTree<String> list=  new PetriniTree<String>();
        final Scanner s = new Scanner(new File("TreeAnimals.txt"));
        while (s.hasNextLine()) {
            String next = s.nextLine();
            list.add(next);
        }
        System.out.println(list);
        final Scanner remove = new Scanner(new File("Remove.txt"));
        while (remove.hasNextLine()) {
            String next = remove.nextLine();
            list.remove(next);
        }
        System.out.println(list);
        s.close();
        remove.close();
    }
}

class PetriniTree<E extends Comparable<E>> {
    private PetriniNode<E> root;

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
        return "\nPrefix:      [ " + this.preorder(this.root) + "]\n" +
               "Infix:       [ " + this.inorder(this.root) + "]\n" + 
               "Postfix      [ " + this.postOrder(this.root) + "]\n";
    }

    public void remove(E e) {
        if (!this.contains(e)) 
            System.out.println("The tree does not contain " + e);
        else {
            this.root = this.remove(this.root, e);
            System.out.println("Removed " + e);
        }
    }

    public boolean contains(E e) {
        return this.contains(this.root, e);
    }

    private boolean contains(PetriniNode<E> current, E e) {
        if (current == null)
            return false;
        if (current.getValue().equals(e))
            return true;
        return this.contains(current.getLeft(), e) || this.contains(current.getRight(), e);
    }

    private PetriniNode<E> remove(PetriniNode<E> node, E value) {
        if (node == null)
            return null;
        if (node.getValue().compareTo(value) > 0) {
            node.setLeft(this.remove(node.getLeft(), value));
            return node;
        } else if (node.getValue().compareTo(value) < 0) {
            node.setRight(this.remove(node.getRight(), value));
            return node;
        }
        if (node.getLeft() == null)
            return node.getRight();
        else if (node.getRight() == null)
            return node.getLeft();
        else {
            PetriniNode<E> parent = node;
            PetriniNode<E> current = node.getRight();
            while (current.getLeft() != null) {
                parent = current;
                current = current.getLeft();
            }
            if (!parent.equals(node))
                parent.setLeft(current.getRight());
            else
                parent.setRight(current.getRight());
            node.setValue(current.getValue());
        }
        return node;
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