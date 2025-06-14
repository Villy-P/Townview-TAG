import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PetriniV11 {
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
        double smallest = Double.MAX_VALUE;
        String smallest2 = "";
        double biggest = 0;
        String biggest2 = "";
        for (String str : letters) {
            PetriniNode<String> node = graph.root.get(str);
            double total = 0;
            for (String e : letters) {
                if (str.equals(e))
                    continue;
                total += node.findDistance(e);
            }
            if (total < smallest) {
                smallest = total;
                smallest2 = node.value;
            }
            if (total > biggest) {
                biggest = total;
                biggest2 = node.value;
            }
            System.out.printf("%s : %.3f\n", node.value, (total / letters.size()));
        }
        System.out.printf(smallest2 + " is at the center with an average distance of %.3f\n", smallest / letters.size());
        System.out.printf(biggest2 + " is the most remote with an average distance of %.3f\n", biggest / letters.size());
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

    public int findDistance(E e) {
        ArrayList<E> alList = new ArrayList<>();
        return this.findDistance(e, alList);
    }

    private int findDistance(E e, ArrayList<E> visited) {
        if (this.value.equals(e))
            return 0;
        int smallest = Integer.MAX_VALUE;
        visited.add(this.value);
        ArrayList<E> localVisited = new ArrayList<>(visited);
        for (PetriniNode<E> item : this.connections) {
            int dist = Integer.MAX_VALUE;
            if (!localVisited.contains(item.value))
                dist = item.findDistance(e, localVisited);
            if (dist < smallest)
                smallest = dist;
        }
        if (smallest == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return smallest + 1;
    }
}