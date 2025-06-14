import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compress {
    public static String directory = "C:\\Users\\Valerius Petrini\\Documents\\Platformer\\src\\";
    public static String mainClass = "GameEngine.java";
    public static File dir = new File(directory);

    public static HashSet<String> imports = new HashSet<String>();

    public static void main(String[] args) throws IOException {
        String content = "";
        Scanner file = new Scanner(new File(directory + mainClass));
        File compressed = new File("GameEngine.java");
        compressed.createNewFile();
        FileWriter writer = new FileWriter("GameEngine.java");
        content += compress(getFileContent(file), true);
        File[] files = dir.listFiles();
        for (File f : files) {
            System.err.println(f.getName());
            if (f.getName().equals(mainClass))
                continue;
            Scanner ff = new Scanner(f);
            content += compress(getFileContent(ff), false);
        }
        for (String s : imports)
            writer.write(s);
        writer.write(content);
        writer.close();
    }

    public static String getFileContent(Scanner file) {
        String output = "";
        while (file.hasNextLine())
            output += file.nextLine() + "\n";
        return output;
    }

    public static String compress(String file, boolean main) {
        String ret = file;
        Matcher m = Pattern.compile("import.*;").matcher(ret);
        while (m.find())
            imports.add(m.group());
        if (!main)
            ret = ret.replace("public class", "class");
        ret = ret.replace("import.*;", "");
        ret = ret.replaceAll("//.*\n", "");
        ret = ret.replaceAll("import.*;", "");
        ret = ret.replace("@Override", "@Override ");
        ret = ret.replace("data/", "");
        ret = ret.replace("assets/", "");
        // ret = ret.replace("GameEngine", "GE");
        return ret;
    }
}