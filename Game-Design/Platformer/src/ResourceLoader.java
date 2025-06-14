import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {
    public static Font mainFont;

    public static void loadFonts() {
        try {
            mainFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets\\font\\CaviarDreams.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mainFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
}
