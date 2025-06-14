import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Pong {
    public static void main(String[] args) {
        GameEngine.main(args);
    }
}

class GameEngine implements Runnable {
    private Thread thread;
    private Window window;
    private Input input;

    private boolean running = false;

    private double frameTime = 0;

    private float scale = 1f;

    private int frames = 0;
    private int fps = 0;

    public static int width = 1000;
    public static int height = 750;

    private String title = "Game Engine";

    private ArrayList<Thing> gameObjects = new ArrayList<>();

    private final double UPDATE_CAP = 1.0 / 60.0;

    public static void main(String[] args) {
        var ge = new GameEngine();
        ge.start();
    }

    public GameEngine() {
        this.gameObjects.add(new Ball());
        this.gameObjects.add(new Paddle(1));
        this.gameObjects.add(new Paddle(2));
    }

    public void start() {
        this.window = new Window(this);
        this.thread = new Thread(this);
        this.input = new Input(this);
        this.thread.run();
    }

    public void stop() {

    }

    public void run() {
        this.running = true;
        double lastTime = Time.getNanoSeconds();
        double timeUnprocessed = 0;
        double timePassed = 0;
        double firstTime = 0;
        boolean render = false;
        while (running) {
            firstTime = Time.getNanoSeconds();
            timePassed = firstTime - lastTime;
            lastTime = firstTime;

            render = false;

            timeUnprocessed += timePassed;

            this.frameTime += timePassed;

            while (timeUnprocessed >= UPDATE_CAP) {
                timeUnprocessed -= UPDATE_CAP;
                render = true;
                if (this.frameTime >= 1.0) {
                    this.fps = this.frames;
                    this.frames = 0;
                    this.frameTime = 0;
                    System.out.println("FPS: " + fps);
                }
            }

            if (render) {
                this.window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception err) {}
            }
        }
    }

    public void dispose() {

    }

    public void update(Graphics g) {
        // DRAW HERE
        for (Thing thing : this.gameObjects) {
            for (Thing obj : this.gameObjects) {
                if (thing == obj)
                    continue;
                if (thing.collides(obj))
                    thing.bounce();
            }
            thing.draw(g, this);
        }
        g.setColor(Color.red);
        g.drawChars(("FPS: " + this.fps).toCharArray(), 1, 5, 0, 0);
    }

    public float getScale() { return this.scale; }
    public String getTitle() { return this.title; }
    public Window getWindow() { return this.window; }
    public Input getInput() { return this.input; }
    public ArrayList<Thing> getObjects() { return this.gameObjects; }
}