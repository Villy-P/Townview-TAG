import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PetriniV14 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("letter.txt"));
        PetriniStructure one = new PetriniStructure();
        String[] objects = s.nextLine().split(" ");
        for (String e : objects)
            one.add(e);
        PetriniStructure two = new PetriniStructure();
        objects = s.nextLine().split(" ");
        for (String e : objects)
            two.add(e);
    
        System.out.println("One:                " + one);
        System.out.println("Two:                " + two);
        System.out.println("Union:              " + one.union(two));
        System.out.println("Intersection:       " + one.intersection(two));
        System.out.println("One Difference Two: " + one.difference(two));
        System.out.println("Two Difference One: " + two.difference(one));
        System.out.println("Symmetric:          " + one.symmetric(two));
        s.close();
    }
}

class PetriniStructure {
    public Object[] arr;

    public PetriniStructure() {
        this.arr = new Object[0];
    }

    public boolean add(Object e) {
        if (this.contains(e))
            return false;
        Object[] temp = new Object[this.arr.length + 1];
        for (int i = 0; i < this.arr.length; i++)
            temp[i] = this.arr[i];
        temp[temp.length  - 1] = e;
        this.arr = temp;
        return true;
    }

    public PetriniStructure union(PetriniStructure other) {
        PetriniStructure ret = new PetriniStructure();
        for (Object i : this.arr)
            ret.add(i);
        for (Object i : other.arr)
            ret.add(i);
        return ret;
    }

    public PetriniStructure intersection(PetriniStructure other) {
        PetriniStructure ret = new PetriniStructure();
        for (Object i : this.arr)
            if (other.contains(i))
                ret.add(i);
        return ret;
    }

    public PetriniStructure difference(PetriniStructure other) {
        PetriniStructure ret = new PetriniStructure();
        for (Object i : this.arr)
            if (!other.contains(i))
                ret.add(i);
        return ret;
    }

    public PetriniStructure symmetric(PetriniStructure other) {
        PetriniStructure ret = new PetriniStructure();
        for (Object i : this.arr)
            if (!other.contains(i))
                ret.add(i);
        for (Object i : other.arr)
            if (!this.contains(i))
                ret.add(i);
        return ret;
    }

    public boolean contains(Object o) {
        for (Object i : this.arr)
            if (i.equals(o))
                return true;
        return false;
    }

    public String toString() {
        String out = "[ ";
        for (Object i : this.arr)
            out += i + " ";
        return out + "]";
    }
}