import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Setup {
    public enum BLOCK_TYPE {
        NONE,
        BASIC
    }

    public static void readLayoutFile(String path, GameEngine engine) {
        try {
            final Scanner scanner = new Scanner(new File(path));
            final int blockW = Integer.parseInt(scanner.nextLine());
            final int blockH = Integer.parseInt(scanner.nextLine());
            engine.tileWidth = blockW;
            engine.tileHeight = blockH;
            int currentLine = -1;
            Player p = null;
            var patrol = new ArrayList<PatrolEnemy>();
            var fly = new ArrayList<FlyingEnemy>();
            var archers = new ArrayList<ArcherEnemy>();
            int[] startPosition = null;
            while (scanner.hasNextLine()) {
                currentLine++;
                engine.position.add(new ArrayList<>());
                final String line = scanner.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {
                        engine.position.get(currentLine).add(0);
                        continue;
                    }
                    if (line.charAt(i) == '1') {
                        engine.position.get(currentLine).add(1);
                        engine.getThings().add(new Ground(i * blockW, currentLine * blockH, blockW, blockH));
                    }
                    if (line.charAt(i) == 'S') {
                        if (startPosition == null) {
                            startPosition = new int[]{i, currentLine};
                            continue;
                        }
                        int width = i - startPosition[0] + 1;
                        int height = currentLine - startPosition[1] + 1;
                        engine.getThings().add(new Ground(startPosition[0] * blockW, startPosition[1] * blockH, blockW * width, blockH * height));
                        System.out.println(startPosition[0] * blockW + " " + startPosition[1] * blockH + " " + blockW * width + " " + blockH * height);
                        startPosition = null;
                    }
                    if (line.charAt(i) == '@')
                        p = new Player(i * blockW, currentLine * blockH);
                    if (line.charAt(i) == 'P')
                        patrol.add(new PatrolEnemy(i * blockW, currentLine * blockH));
                    if (line.charAt(i) == 'F')
                        fly.add(new FlyingEnemy(i * blockW, currentLine * blockH));
                    if (line.charAt(i) == 'A')
                        archers.add(new ArcherEnemy(i * blockW, currentLine * blockH));
                    if (line.charAt(i) == '2')
                        engine.getThings().add(new Platform(i * blockW, currentLine * blockH));
                    if (line.charAt(i) == 'E')
                        engine.getThings().add(new End(i * blockW, currentLine * blockH, blockW, blockH));
                    if (line.charAt(i) == 'M')
                        engine.getThings().add(new Text(i * blockW, currentLine * blockH, "Press I for controls"));
                    if (line.charAt(i) == 'O')
                        engine.getThings().add(new Text(i * blockW, currentLine * blockH, "You have won"));
                }
            }
            engine.getThings().addAll(patrol);
            for (FlyingEnemy f : fly)
                f.p = p;
            for (ArcherEnemy a : archers)
                a.p = p;
            engine.getThings().addAll(fly);
            engine.getThings().addAll(archers);
            engine.getThings().add(p);
            engine.getThings().add(new HealthBar(p));
            engine.setCamera(new Camera(engine, p));
        } catch (IOException e) {
            System.err.println("There was an error reading input file " + path);
        }
    }
}
