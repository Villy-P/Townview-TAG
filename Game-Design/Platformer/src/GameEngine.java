import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class GameEngine implements Runnable {
    private Thread thread;
    private Window window;
    private Input input;
    private Camera camera;

    private boolean running = false;

    private double frameTime = 0;

    private float scale = 1f;

    private int level = 1;

    private int frames = 0;
    private int fps = 0;
    private int width = 1000;
    private int height = 750;

    public boolean gameOver = false;

    private String title = "Game Engine";
    public boolean showInstructions = false;

    private ArrayList<Thing> things = new ArrayList<>();

    private final double UPDATE_CAP = 1.0 / 60.0;

    public static int arrows = 0;

    public Instructions instructions;

    public ArrayList<ArrayList<Integer>> position = new ArrayList<>();

    public int tileWidth;
    public int tileHeight;

    public static void main(String[] args) {
        var ge = new GameEngine();
        ge.start();
    }

    public GameEngine() {
        ResourceLoader.loadFonts();
        this.instructions = new Instructions(this);
        this.setup();
    }

    public void start() {
        this.window = new Window(this);
        this.thread = new Thread(this);
        this.input = new Input(this);
        this.thread.run();
    }

    public void setup() {
        this.gameOver = false;
        this.things.clear();
        Collision.clearCollisions();
        arrows = 0;
        Setup.readLayoutFile("data/level" + this.level + ".dat", this);

        BiConsumer<Thing, Thing> onPlayerCollideEnemy = (t, o) -> {
            if (!((Player)t).dashing && !((Enemy)o).dead)
                ((Player)t).changeHealth(-10, this);
            if (((Player)t).iframe == 0)
                ((Player)t).iframe = 40;
        };
        BiConsumer<Thing, Thing> onArrowCollideGround = (t, o) -> {
            ((Arrow)t).update = false;
            t.stopMovingOnCollision = true;
        };
        BiConsumer<Thing, Thing> onArrowCollideEnemy = (t, o) -> {
            if (!((Arrow)t).update || ((Arrow)t).stickTo != null)
                return;
            ((Arrow)t).stickTo = o;
            ((Enemy)o).health -= ((Arrow)t).damage;
        };
        BiConsumer<Thing, Thing> onEnemyArrowHit = (t, o) -> {
            if (!((Player)o).dashing)
                ((Player)o).changeHealth(-15, this);
            if (((Player)o).iframe == 0)
                ((Player)o).iframe = 50;
            t.checkCollision = true;
            ((Arrow)t).update = true;
            t.stopMovingOnCollision = false;
        };
        BiConsumer<Thing, Thing> onEnd = (t, o) -> {
            this.things.clear();
            GameEngine.arrows = 0;
            Setup.readLayoutFile("data/level" + ++this.level + ".dat", this);
        };

        Collision.createNewCollision(Collision.CollisionsType.PLAYER, Collision.CollisionsType.GROUND, Collision.CollisionsPosition.ALL, null);
        Collision.createNewCollision(Collision.CollisionsType.ENEMY, Collision.CollisionsType.GROUND, Collision.CollisionsPosition.ALL, null);
        Collision.createNewCollision(Collision.CollisionsType.ARROW, Collision.CollisionsType.GROUND, Collision.CollisionsPosition.ALL, onArrowCollideGround);
        Collision.createNewCollision(Collision.CollisionsType.ENEMY_ARROW, Collision.CollisionsType.GROUND, Collision.CollisionsPosition.ALL, onArrowCollideGround);
        Collision.createNewCollision(Collision.CollisionsType.ENEMY_ARROW, Collision.CollisionsType.PLAYER, Collision.CollisionsPosition.NONE, onEnemyArrowHit);
        Collision.createNewCollision(Collision.CollisionsType.PLAYER, Collision.CollisionsType.ENEMY, Collision.CollisionsPosition.ALL, onPlayerCollideEnemy, false);
        Collision.createNewCollision(Collision.CollisionsType.ARROW, Collision.CollisionsType.ENEMY, Collision.CollisionsPosition.ALL, onArrowCollideEnemy);
        Collision.createNewCollision(Collision.CollisionsType.PLAYER, Collision.CollisionsType.END, Collision.CollisionsPosition.ALL, onEnd);
    }

    public void reset() {
        this.thread = null;
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
                    // System.out.println(this.fps);
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
        if (GameEngine.arrows > Constants.MAX_ARROW_ON_SCREEN) {
            for (int i = 0; i < this.things.size(); i++) {
                if (this.things.get(i) instanceof Arrow) {
                    this.things.remove(i);
                    GameEngine.arrows--;
                    break;
                }
            }
        }
        if (this.gameOver) {
            this.things.clear();
            this.things.add(new GameOver());
        }
        if (this.input.isKeyDown(KeyEvent.VK_I)) {
            this.showInstructions = true;
            this.things.add(new Instructions(this));
        }
        if (this.showInstructions) {
            this.instructions.draw(g, this);
            return;
        }
        Player p = null;
        for (int i = 0; i < this.things.size(); i++) {
            Thing obj = this.things.get(i);
            if (obj instanceof Player)
                p = (Player)obj;
            if (Thing.isInFrame(obj, this))
                obj.draw(g, this);
            obj.update(this);
            Collision.handleCollisions(this);
        }
        if (p != null) {
            this.getCamera().setX(p.prevX - p.x);
            this.getCamera().setY(p.prevY - p.y);
        }
        g.setFont(ResourceLoader.mainFont);
        g.drawString("FPS: " + this.fps, this.width - 100, 20);
    }

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public float getScale() { return this.scale; }
    public Input getInput() { return this.input; }
    public String getTitle() { return this.title; }
    public Camera getCamera() { return this.camera; }
    public Window getWindow() { return this.window; }
    public ArrayList<Thing> getThings() { return this.things; }

    public void setCamera(Camera camera) { this.camera = camera; }
}