package Lab2;
import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class PetriniV02 {
    public static void main(String[] args) throws IOException {
        Vector<String> ds = new Vector<>();
        Scanner s = new Scanner(new File("Lab2\\CS302.txt"));
        while (s.hasNext()) {
            String command = s.next();
            int times = s.nextInt();
            s.nextLine();

            if (command.equals("Empty"))
                System.out.println("Empty: " + ds.empty());
            else if (command.equals("Add"))
                for (int i = 0; i < times; i++)
                    ds.add(s.nextLine());
            else if (command.equals("Find")) {
                for (int i = 0; i < times; i++) {
                    String subCommand = s.nextLine();
                    System.out.println(subCommand + " index is " + ds.find(subCommand));
                }
            } else if (command.equals("Print"))
                System.out.println(ds);
            else if (command.equals("Get"))
                for (int i = 0; i < times; i++)
                    System.out.println(ds.get() + " was fetched");
            else if (command.equals("Remove"))
                for (int i = 0; i < times; i++)
                    System.out.println(ds.remove() + " was removed");
        }
    }
}

class Vector<T> {
    private Object[] arr = new Object[0];

    public void add(T t) {
        Object[] temp = new Object[this.arr.length + 1];
        for (int i = 0; i < this.arr.length; i++)
            temp[i] = this.arr[i];
        temp[this.arr.length] = t;
        this.arr = temp;
    }

    @SuppressWarnings("unchecked")
    public T remove() {
        Object[] temp = new Object[this.arr.length - 1];
        for (int i = 0; i < temp.length; i++)
            temp[i] = this.arr[i];
        Object ret = this.arr[this.arr.length - 1];
        this.arr = temp;
        return (T)ret;
    }

    @SuppressWarnings("unchecked")
    public T get() {
        return this.empty() ? null : (T)this.arr[this.arr.length - 1];
    }

    public int find(T t) {
        for (int i = 0; i < this.arr.length; i++)
            if (this.arr[i] == t)
                return i;
        return -1;
    }

    public boolean empty() {
        return this.arr.length == 0;
    }

    public String toString() {
        String out = "[ ";
        for (int i = 0; i < this.arr.length; i++) {
            out += this.arr[i];
            if (i != this.arr.length - 1)
                out += " , ";
        }
        return out + " ]";
    }
}