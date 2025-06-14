import java.util.ArrayList;

public class PetriniV20 {
    public ArrayList<String> strs = new ArrayList<>();

    public static void main(String[] tacos) {
        PetriniV20 m = new PetriniV20();
        m.makeStrings();
        final int longestLength = m.getLongestStringLength();
        System.out.println("*".repeat(longestLength + 4));
        for (String s : m.strs)
            System.out.println("*" + m.center(s, longestLength + 2) + "*");
        System.out.println("*".repeat(longestLength + 4));
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }   

    public void makeStrings() {
        final int strings = PetriniV20.getRandomNumber(10, 30);
        for (int i = 0; i < strings; i++) {
            final int chars = PetriniV20.getRandomNumber(5, 25);
            String str = "";
            for (int j = 0; j < chars; j++)
                str += (char) PetriniV20.getRandomNumber(65, 90);
            this.strs.add(str);
        }
    }

    public int getLongestStringLength() {
        int longest = 0;
        for (String s : this.strs)
            if (s.length() > longest)
                longest = s.length();
        return longest;
    }

    public String center(String s, int w) {
        String ss = "";
        String spaces = "";
        int pad = w - s.length();                  
        for(int i = 0; i < pad / 2; ++i)
            spaces += " ";
        ss += spaces + s + spaces;
        if (pad > 0 && pad % 2 != 0)                    
            ss += " ";
        return ss;
    }
}