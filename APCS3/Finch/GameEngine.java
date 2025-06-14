import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

class GameEngine implements Runnable {
    private Thread thread;
    private Window window;
    private Input input;

    // private boolean running = false;

    // private double frameTime = 0;

    private float scale = 1f;

    // private int frames = 0;
    // private int fps = 0;

    // public static int width = 1000;
    // public static int height = 750;

    private String title = "Game Engine";


    // private final double UPDATE_CAP = 1.0 / 60.0;

    public static void main(String[] args) {
        var ge = new GameEngine();
        ge.start();
    }

    public GameEngine() {
        this.start();
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

    }

    public void dispose() {

    }

    public void update(Graphics g) {
        // DRAW HERE
        
    }

    public float getScale() { return this.scale; }
    public String getTitle() { return this.title; }
    public Window getWindow() { return this.window; }
    public Input getInput() { return this.input; }
}