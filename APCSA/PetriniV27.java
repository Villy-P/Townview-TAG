import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.net.URL;
import java.io.File;

public class PetriniV27 {
	public static void main(String[] tacos) {
		ArrayList<String> linesOfText = new ArrayList<String>();
		linesOfText = readText(linesOfText);
		linesOfText = formatText(linesOfText);
        System.out.println("This text :");
        System.out.println(String.format("Contains %d words and %d unique words", getWords(linesOfText).size(), getUniqueWords(linesOfText).size()));
        System.out.println(String.format("Each word has an average length of %.2f letters", getAverageLetters(linesOfText)));
        System.out.println("The top fifteen most common words are :");
        for (int i = 0; i < 15; i++)
            System.out.println(getCommonWords(linesOfText).get(i).key + " : " + getCommonWords(linesOfText).get(i).value);
        System.out.println("\nWhat word would you like to find?");
        Scanner s = new Scanner(System.in);
        String e = s.nextLine();
        System.out.println("\nThe word \"" + e + "\" was found " + getWordCount(linesOfText).get(e) + " times.");
	}

    public static HashMap<String, Integer> getWordCount(ArrayList<String> linesOfText) {
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String word : getWords(linesOfText)) {
            if (wordCount.containsKey(word))
                wordCount.put(word, wordCount.get(word) + 1);
            else
                wordCount.put(word, 1);
        }
        return wordCount;
    }

    public static ArrayList<KeyValue> getCommonWords(ArrayList<String> linesOfText) {
        HashMap<String, Integer> wordCount = getWordCount(linesOfText);
        HashSet<String> uniqueWords = getUniqueWords(linesOfText);

        ArrayList<KeyValue> list = new ArrayList<>();
        for (String key : uniqueWords)
            if (wordCount.containsKey(key))
                list.add(new KeyValue(key, wordCount.get(key)));

        Collections.sort(list, Collections.reverseOrder());

        return list;
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

    public static HashSet<String> getUniqueWords(ArrayList<String> linesOfText) {
        HashSet<String> count = new HashSet<String>();
        for (String s : getWords(linesOfText))
            count.add(s);
        return count;
    }

    public static double getAverageLetters(ArrayList<String> linesOfText) {
        int count = 0;
        for (String s : getWords(linesOfText))
            count += s.length();
        return (double) count / getWords(linesOfText).size();
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