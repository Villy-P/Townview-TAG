import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class PetriniV16 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("Food.txt"));
        PetriniStructure<String> e = new PetriniStructure<String>();
        while (s.hasNextLine())
            e.add(s.nextLine());
        System.out.println(e);
        s.close();
    }
}

class PetriniStructure<E> {
    private LinkedList<PetriniNode<E>>[] array;

    // I have no idea what this does, VSCode has a warning without it.
    @SuppressWarnings("unchecked")
    public PetriniStructure() {
        this.array = new LinkedList[10];
        for (int i = 0; i < 10; i++)
            this.array[i] = new LinkedList<>();
    }

    public boolean add(E e) {
        var n = new PetriniNode<E>(e);
        for (var s : this.array[n.getHash()])
            if (s.getValue().equals(n.getValue()))
                return false;
        this.array[n.getHash()].add(n);
        return true;
    }

    @Override
    public String toString() {
        String ret = "";
        for (var e : this.array) {
            String add = "[";
            for (var s : e)
                add += s + ", ";
            if (add.length() > 2)
                add = add.substring(0, add.length() - 2);
            ret += add + "]\n";
        }
        return ret;
    }
}

class PetriniNode<E> {
    private E value;

    public PetriniNode(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    public int getHash() {
        int hash = ((Object)value).hashCode();
        if (hash < 0)
            hash = Math.abs(hash);
        if (hash > 10)
            hash = hash % 10;
        return hash;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}