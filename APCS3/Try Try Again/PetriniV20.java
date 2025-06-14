import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

public class PetriniV20 {
    public static void main(String[] args) throws FileNotFoundException {
        PetriniTree tree = new PetriniTree();
        Scanner s = new Scanner(new File("TWords.txt"));
        Scanner k = new Scanner(System.in);
        while (s.hasNextLine())
            tree.add(s.nextLine());
        while (true) {
            System.out.print("\nEnter a word : ");
            String input = k.nextLine();
            if (input == "exit")
                break;
            System.out.println(input + " was" + (tree.isValidWord(input) ? "" : " not") + " found");
        }
        s.close();
        k.close();
    }
}

class PetriniTree {
    public PetriniNode head = new PetriniNode("T", false);

    public void add(String e) {
        PetriniNode current = this.head;
        for (int i = 1; i < e.length(); i++) {
            String c = e.charAt(i) + "";
            PetriniNode child = current.getChild(c);
            if (child == null) {
                PetriniNode node = new PetriniNode(c, i == e.length() - 1);
                current.children.add(node);
                current = node;
            } else {
                current = child;
            }
        }
        current.isEnd = true;
    }

    public boolean isValidWord(String e) {
        PetriniNode current = this.head;
        for (int i = 1; i < e.length(); i++) {
            String c = e.charAt(i) + "";
            PetriniNode child = current.getChild(c);
            if (child == null)
                return false;
            else
                current = child;
        }
        return current.isEnd;
    }
}

class PetriniNode {
    public boolean isEnd;
    public String value;
    public ArrayList<PetriniNode> children = new ArrayList<>();

    public PetriniNode(String value, boolean end) {
        this.value = value;
        this.isEnd = end;
    }

    public PetriniNode getChild(String s) {
        for (var v : this.children)
            if (v.value.equals(s))
                return v;
        return null;
    }

    @Override
    public String toString() {
        return this.value + " " + this.isEnd;
    }
}