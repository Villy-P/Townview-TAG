import java.util.ArrayList;
import java.util.Scanner;
import java.net.URL;
import java.io.File;

public class PetriniV28 {
	public static void main(String[] tacos) {
		ArrayList<String> linesOfText = new ArrayList<String>();
		linesOfText = readText(linesOfText);
		linesOfText = formatText(linesOfText);
        System.out.println(sort(getWords(linesOfText)));
	}

    public static ArrayList<String> sort(ArrayList<String> s) {
        for (int i = 0; i < s.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < s.size(); j++)
                if (s.get(j).compareTo(s.get(min)) < 0)
                    min = j;
            String temp = s.get(i);
            s.set(i, s.get(min));
            s.set(min, temp);
        }
        return s;
    } 

    public static ArrayList<String> getWords(ArrayList<String> linesOfText) {
        ArrayList<String> count = new ArrayList<>();
        for (String e : linesOfText) {
            for (String s : e.split(" ")) {
                if (s.equals(""))
                    continue;
                count.add(s.toLowerCase());
            }
        }
        return count;
    }

	public static ArrayList<String> readText(ArrayList<String> text) {
		String website = "http://archive.org/stream/TheEpicofGilgamesh_201606/eog_djvu.txt";
		try {
            URL url = new URL(website);
            Scanner s = new Scanner(url.openStream());
            while(s.hasNext())
                text.add(s.nextLine());
        } catch(Exception e) {
            try{
                Scanner s = new Scanner(new File("pride.txt"));
                while(s.hasNext())
                    text.add(s.nextLine());
            } catch(Exception ex) {}
        }
		return text;
	}

	public static ArrayList<String> formatText(ArrayList<String> text) {
        for (int line = 0; line < text.size(); line++) {
        	String s = text.get(line);
			if (s.contains("The Epic Of Gilgamesh") && !s.contains("<")) {
                for(int a = line - 1; a >= 0; a--)
                    text.remove(a);
                break;
            }
        }
		for (int line = 0; line < text.size(); line++) {
            String s = text.get(line);
			text.set(line, text.get(line).replaceAll("[^a-zA-Z ]", ""));
			if (s.contains("</pre>"))
                for (int a = line - 5; a < text.size(); text.remove(a));
        }
		return text;
	}
}

class KeyValue implements Comparable<KeyValue> {
    public String key;
    public int value;
    
    public KeyValue(String key, int value) {
        this.key = key;
        this.value = value;
    }
    
    public int compareTo(KeyValue other) {
        return this.value - other.value;
    }
}