import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PetriniV10 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("Connections.txt"));
        PetriniGraph<String> graph = new PetriniGraph<>();
        ArrayList<String> letters = new ArrayList<>();
        while (s.hasNextLine()) {
            String[] parts = s.nextLine().split(" : ");
            if (graph.root == null)
                graph.root = new PetriniNode<String>(parts[0]);
            PetriniNode<String> val = graph.root.get(parts[0]); 
            if (!letters.contains(parts[0]))
                letters.add(parts[0]);
            String[] add = parts[1].split(" ");
            for (String str : add) {
                if (!letters.contains(str))
                    letters.add(str);
                val.add(str);
            }
        }
        Collections.sort(letters);
        for (String str : letters) {
            PetriniNode<String> node = graph.root.get(str);
            System.out.print(node.value + "\t");
            for (PetriniNode<String> child : node.connections)
                System.out.print(child.value + " ");
            System.out.println();
        }
        s.close();
    }
}

class PetriniGraph<E> {
    public PetriniNode<E> root;
}

class PetriniNode<E> {
    public E value;
    public ArrayList<PetriniNode<E>> connections = new ArrayList<>();

    public PetriniNode(E value) {
        this.value = value;
    }

    public PetriniNode<E> get(E e) {
        ArrayList<E> nodes = new ArrayList<>();
        return this.get(e, nodes);
    }

    private PetriniNode<E> get(E e, ArrayList<E> nodes) {
        if (this.value.equals(e)) 
            return this;
        nodes.add(this.value);
        for (PetriniNode<E> other : this.connections) {
            if (nodes.contains(other.value))
                continue;
            PetriniNode<E> temp = other.get(e, nodes);
            if (temp != null)
                return temp;
        }
        return null;
    }

    public void add(E e) {
        PetriniNode<E> temp = this.get(e);
        for (PetriniNode<E> node : this.connections)
            if (node.value == e)
                return;
        if (temp == null)
            temp = new PetriniNode<E>(e);
        this.connections.add(temp);
        temp.connections.add(this);
    }
}