import java.awt.Graphics2D;import java.awt.event.MouseWheelListener;import java.awt.event.MouseEvent;import java.awt.Font;import javax.swing.JFrame;import java.awt.geom.AffineTransform;import java.util.ArrayList;import javax.imageio.ImageIO;import java.awt.event.KeyListener;import java.awt.FontMetrics;import java.awt.FontFormatException;import java.awt.event.MouseWheelEvent;import java.awt.Canvas;import java.awt.BorderLayout;import java.io.IOException;import java.awt.image.BufferedImage;import java.awt.event.MouseListener;import java.util.Arrays;import java.awt.Color;import java.awt.event.MouseMotionListener;import java.awt.image.BufferStrategy;import java.util.function.BiConsumer;import java.awt.event.KeyEvent;import java.awt.Dimension;import java.awt.GraphicsEnvironment;import java.util.Scanner;import java.awt.Graphics;import java.io.File;




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
        Setup.readLayoutFile("level" + this.level + ".dat", this);

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
            Setup.readLayoutFile("level" + ++this.level + ".dat", this);
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


class ArcherEnemy extends Enemy {
    public Player p;

    public Arrow currentArrow = null;

    public boolean firing = false;

    public double maxShootingTime = 20.0;
    public double shootingTime = 20.0;
    public double shootingReduceTime = 0.2;

    public ArcherEnemy(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.height = this.width = 50;
        this.color = Color.ORANGE;
        this.healthBarWidth = this.width - 20;
        this.maxHealth = 80.0;
        this.health = 80.0;
        this.onground = false;
        this.stopMovingOnCollision = true;
    }

    @Override 
    public void update(GameEngine ge) {
        this.shootingTime -= this.shootingReduceTime;
        if (this.shootingTime <= 0) {
            this.releaseArrow(ge);
            this.shootingTime = this.maxShootingTime;
        }
        this.prevX = this.x;
        this.prevY = this.y;
        this.dy += Constants.GRAVITY;
        this.y += this.dy;
        if (this.dead) {
            this.health = 0;
            this.dy = 0;
            return;
        }
        this.x += this.dx;
        if (this.health <= 0) {
            this.dead = true;
            this.dx = 0;
            this.dy = 0;
        }
        if (!this.firing)
            this.beginShootArrow(ge);
        if (this.currentArrow != null)
            this.setRotationalForce(ge);
    }

    public void beginShootArrow(GameEngine ge) {
        this.currentArrow = new Arrow(this.x, this.y, 0, 0);
        this.currentArrow.checkCollision = false;
        ge.getThings().add(currentArrow);
        this.firing = true;
    }

    public void setRotationalForce(GameEngine ge) {
        double dx = this.p.x - this.x;
        double dy = this.p.y - this.y;
        double angle = Math.atan2(dy, dx) + Math.toRadians(90);
        this.currentArrow.rotation = angle;
    }

    public void releaseArrow(GameEngine ge) {
        if (this.currentArrow == null)
            return;
        double dx = this.p.x - this.x;
        double dy = this.p.y - this.y;
        double angle = Math.atan2(dy, dx) + Math.toRadians(90);
        double forceX = Math.toDegrees(Math.sin(angle)) * Constants.MAX_ARROW_FORCE;
        double forceY = Math.toDegrees(Math.cos(angle)) * Constants.MAX_ARROW_FORCE;
        this.currentArrow.dx = forceX;
        this.currentArrow.dy = -forceY;
        this.currentArrow.update = true;
        this.currentArrow.cameraAffect = true;
        this.currentArrow.currentlyFiring = false;
        this.currentArrow.collisionID = Collision.CollisionsType.ENEMY_ARROW;
        this.currentArrow.checkCollision = true;
        this.currentArrow = null;
        this.firing = false;
    }
}










class Arrow extends Thing {
    public BufferedImage img;

    public double rotation;

    public boolean update = false;
    public boolean currentlyFiring = true;

    public Thing stickTo = null;

    public double damage = 15;

    public Arrow(double x, double y, double dx, double dy) {
        GameEngine.arrows++;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.width = 10;
        this.height = 30;
        this.collisionID = Collision.CollisionsType.ARROW;
        this.stopMovingOnCollision = true;
        try {
            this.img = ImageIO.read(new File("image/arrow.png"));
        } catch (IOException e) {
            System.out.println("There was an error reading image image/arrow.png");
        }
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(this.rotation, this.x + (this.width / 2), this.y + (this.height / 2));
        g2d.drawImage(this.img, null, (int)this.x, (int)this.y);
        g2d.setTransform(old);
    }

    @Override 
    public void update(GameEngine ge) {
        this.prevX = this.x;
        this.prevY = this.y;
        if (this.stickTo != null) {
            this.dx = this.stickTo.x - this.stickTo.prevX;
            this.dy = this.stickTo.y - this.stickTo.prevY;
            this.x += this.dx;
            this.y += this.dy;
            return;
        }
        if ((this.dy != 0 && this.dx != 0) || this.currentlyFiring)
            this.rotation = this.getRotationDegrees(ge);
        this.y += this.dy;
        this.x += this.dx;
        if (!this.update)
            return;
        this.dy += Constants.GRAVITY;
    }

    public double getRotationDegrees(GameEngine ge) {
        final double nextX = this.x + this.dx;
        final double nextY = this.y + this.dy;
        final double rotateBy = Math.atan2(nextY - this.y, nextX - this.x);
        return rotateBy + Math.toRadians(90);
    }
}








class Button extends Thing {
    public String text;

    public Font buttonFont = ResourceLoader.mainFont.deriveFont(20f);

    public BiConsumer<Integer, Integer> onClick;

    public Button(int x, int y, int w, int h, String t) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.text = t;
        this.cameraAffect = false;
        this.color = Color.BLACK;
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.WHITE);
        g.setFont(this.buttonFont);
        g.fillRect((int)this.x - 1, (int)this.y - 1, this.width + 2, this.height + 2);
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
        
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int stringX = metrics.stringWidth(this.text) / 2;
        g.setColor(Color.WHITE);
        g.drawString(this.text, (int)(this.x + (this.width) / 2) - stringX, (int)this.y + this.height - 10);
        this.update(ge);
    }

    @Override 
    public void update(GameEngine ge) {
        if (ge.getInput().isButton(MouseEvent.BUTTON1) && this.mouseInBounds(ge.getInput()))
            this.onClick.accept(ge.getInput().getMouseX(), ge.getInput().getMouseY());
    }

    public boolean mouseInBounds(Input input) {
        int x = input.getMouseX();
        int y = input.getMouseY();
        return x > this.x && 
               y > this.y && 
               x < this.x + this.width && 
               y < this.y + this.height;
    }
}
class Camera {
    public double x;
    public double y;

    public GameEngine ge;

    public Camera(GameEngine ge, Player p) {
        this.ge = ge;
        for (Thing t : this.ge.getThings()) {
            if (!t.cameraAffect)
                continue;
            t.x -= p.x - ge.getWidth() / 2;
            t.y -= p.y - ge.getHeight() / 2;
        }
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public void setX(double x) {
        this.x = x;
        this.update(x, 0);
    }

    public void setY(double y) {
        this.y = y;
        this.update(0, y);
    }

    private void update(double x, double y) {
        for (Thing t : this.ge.getThings()) {
            if (!t.cameraAffect)
                continue;
            t.x += x;
            t.y += y;
        }
    }
}



class Collision {
    public CollisionsType thing;
    public CollisionsType other;
    public CollisionsPosition position;
    public BiConsumer<Thing, Thing> onCollide;
    public boolean preventMove = true;

    public Collision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide) {
        this.thing = thing;
        this.other = other;
        this.position = position;
        this.onCollide = onCollide;
    }

    public Collision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide, boolean preventMove) {
        this.thing = thing;
        this.other = other;
        this.position = position;
        this.onCollide = onCollide;
        this.preventMove = preventMove;
    }

    private static ArrayList<Collision> collisions = new ArrayList<>();

    public static enum CollisionsType {
        PLAYER,
        SCREEN_BOTTOM,
        ARROW,
        GROUND,
        ENEMY,
        ENEMY_ARROW,
        END
    }

    public static enum CollisionsPosition {
        ALL,
        TOP,
        BOTTOM,
        RIGHT,
        LEFT,
        NONE
    }

    public static void createNewCollision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide) {
        collisions.add(new Collision(thing, other, position, onCollide));
    }

    public static void createNewCollision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide, boolean preventMove) {
        collisions.add(new Collision(thing, other, position, onCollide, preventMove));
    }

    private static ArrayList<Thing> getThings(Collision c, GameEngine ge) {
        var a = new ArrayList<Thing>();
        for (Thing t : ge.getThings())
            if (t.collisionID == c.thing)
                a.add(t);
        return a;
    }

    private static ArrayList<Thing> getOthers(Collision c, GameEngine ge) {
        var a = new ArrayList<Thing>();
        for (Thing t : ge.getThings())
            if (t.collisionID == c.other)
                a.add(t);
        return a;
    }

    public static void clearCollisions() {
        collisions.clear();
    }
    
    public static void handleCollisions(GameEngine ge) {
        for (Collision c : collisions) {
            for (Thing t : getThings(c, ge)) {
                if (!t.checkCollision)
                    continue;
                FOR:
                for (Thing o : getOthers(c, ge)) {
                    if (!t.collides(o))
                        continue FOR;
                    if (c.onCollide != null)
                        c.onCollide.accept(t, o);
                    if (t.stopMovingOnCollision) {
                        t.dx = 0;
                        t.dy = 0;
                        if (t instanceof Arrow && !((Arrow)t).currentlyFiring)
                            t.checkCollision = false;
                    }
                    if (c.position == CollisionsPosition.NONE)
                        return;
                    if (t.prevY + t.height <= o.y && c.preventMove) {
                        t.y = o.y - t.height;
                        t.dy = 0;
                        t.onground = true;
                    }
                    if (t.prevY >= o.y + o.height && c.preventMove) {
                        t.dy /= 2;
                        t.y = o.y + o.height;
                    }
                    if (!t.collides(o))
                        continue FOR;
                    if (t.prevX + t.width <= o.x && c.preventMove)
                        t.x = o.x - t.width;
                    if (t.prevX >= o.x + o.width && c.preventMove)
                        t.x = o.x + o.width;
                }
            }
        }
    }
}
class Constants {
    public final static double GRAVITY = 0.3;
    public final static double JUMP = -13;
    public final static double MAX_ARROW_FORCE = 0.55;
    public final static double MIN_ARROW_FORCE = 0.15;
    public final static double ARROW_FORCE = 0.01;
    public final static int MAX_ARROW_ON_SCREEN = 200;
}
class End extends Thing {
    public End(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.collisionID = Collision.CollisionsType.END;
    }
}



class Enemy extends Thing {
    public double maxHealth = 100.0;
    public double health = 100.0;

    public double healthBarWidth;

    public boolean dead = false;

    public Enemy() {
        this.collisionID = Collision.CollisionsType.ENEMY;
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
        g.setColor(Color.RED);
        g.fillRect((int)this.x + 10, (int)this.y - 10, (int)((this.healthBarWidth / this.maxHealth) * this.health), 5);
    }
}


class FlyingEnemy extends Enemy {
    public Player p;

    public FlyingEnemy(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.height = this.width = 50;
        this.color = Color.BLUE;
        this.healthBarWidth = this.width - 20;
        this.maxHealth = 50.0;
        this.health = 50.0;
        this.onground = false;
        this.stopMovingOnCollision = true;
    }

    @Override 
    public void update(GameEngine ge) {
        this.prevX = this.x;
        this.prevY = this.y;
        this.y += this.dy;
        if (this.dead) {
            this.health = 0;
            this.dx = 0;
            this.dy += Constants.GRAVITY;
            return;
        }
        this.x += this.dx;
        if (this.health <= 0) {
            this.dead = true;
            this.dx = 0;
            this.dy = 0;
        }
        this.setVelocity(ge);
    }

    public void setVelocity(GameEngine ge) {
        double dx = this.p.x - this.x;
        double dy = this.p.y - this.y;
        double angle = Math.atan2(dy, dx) + Math.toRadians(90);
        double forceX = Math.toDegrees(Math.sin(angle)) * 0.05;
        double forceY = Math.toDegrees(Math.cos(angle)) * 0.05;
        this.dx = forceX;
        this.dy = -forceY;
    }
}





class GameOver extends Thing {
    public boolean draw = true;

    public Font gameOverFont = ResourceLoader.mainFont.deriveFont(70f);

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ge.getWidth(), ge.getWidth());

        g.setFont(this.gameOverFont);
        
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int stringX = metrics.stringWidth("GAME OVER") / 2;
        g.setColor(Color.RED);
        g.drawString("GAME OVER", (ge.getWidth() / 2) - stringX, 300);

        Button b = new Button(ge.getWidth() / 2 - 100, 500, 200, 30, "Restart");
        Button b2 = new Button(ge.getWidth() / 2 - 100, 550, 200, 30, "Quit");

        b.onClick = (x, y) -> {
            ge.setup();
        };
        b2.onClick = (x, y) -> {
            System.exit(0);
        };

        b.draw(g, ge);
        b2.draw(g, ge);
    }
}



class Ground extends Thing {
    public Ground(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.dx = 0;
        this.dy = 0;
        this.width = width;
        this.height = height;
        this.color = Color.GREEN;
        this.collisionID = Collision.CollisionsType.GROUND;
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
    }

    public static void createGround(GameEngine ge) {
        ge.getThings().add(new Ground(0, ge.getHeight() - 100, 1000, 100));
        ge.getThings().add(new Ground(800, ge.getHeight() - 300, 100, 200));
    }
}








class HealthBar extends Thing {
    private Player player;
    public BufferedImage img;

    public HealthBar(Player player) {
        super();
        this.x = 70;
        this.y = 25;
        this.player = player;
        this.cameraAffect = false;
        try {
            this.img = ImageIO.read(new File("image/dash.png"));
        } catch (IOException e) {
            System.out.println("There was an error reading image image/dash.png");
        }
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.WHITE);
        g.drawRect((int)this.x, (int)this.y, (int)player.maxHealth * 2, 20);
        g.setColor(Color.RED);
        g.fillRect((int)this.x + 1, (int)this.y + 1, (int)player.health * 2 - 1, 19);
        g.setColor(Color.BLUE);
        if (this.player.dashing)
            g.fillRect(10, 10 + (int)(this.player.dashSpeed * 2.5), 50, (int)(this.player.maxDashSpeed * 2.5) - (int)(this.player.dashSpeed * 2.5));
        else 
            g.fillRect(10, 10, 50, 50);
        g.drawImage(this.img, 10, 10, null);
    }
}








class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	private boolean[] keys = new boolean[256];
	private boolean[] keysLast = new boolean[256];

	private boolean[] buttons = new boolean[5];
	private boolean[] buttonsLast = new boolean[5];

	private int mouseX;
    private int mouseY;
	private int scroll;

	private GameEngine ge;

	public Input(GameEngine ge) {
		this.ge = ge;
		this.mouseX = this.mouseY = this.scroll = 0;

		this.ge.getWindow().getCanvas().addKeyListener(this);
		this.ge.getWindow().getCanvas().addMouseListener(this);
		this.ge.getWindow().getCanvas().addMouseMotionListener(this);
		this.ge.getWindow().getCanvas().addMouseWheelListener(this);
	}

	public boolean isKey(int keyCode) {
		return this.keys[keyCode];
	}

	public boolean isKeyUp(int keyCode) {
		return this.keysLast[keyCode] && !this.keys[keyCode];
	}

	public boolean isKeyDown(int keyCode) {
		return !this.keysLast[keyCode] && this.keys[keyCode];
	}

	public boolean isButton(int buttonCode) {
		return this.buttons[buttonCode];
	}

	public boolean isButtonUp(int buttonCode) {
		return this.buttonsLast[buttonCode] && !this.buttons[buttonCode];
	}

	public boolean isButtonDown(int buttonCode) {
		return !this.buttonsLast[buttonCode] && this.buttons[buttonCode];
	}

	public void update() {
        System.out.println(this.scroll);
		this.scroll = 0;
		for(int i = 0; i < this.keys.length; i++)
			this.keysLast[i] = this.keys[i];
		for(int i = 0; i < this.buttons.length; i++)
			this.buttonsLast[i] = this.buttons[i];
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		this.scroll = e.getWheelRotation();
	}

	public void mouseDragged(MouseEvent e) {
		this.mouseX = (int)(e.getX() / this.ge.getScale());
		this.mouseY = (int)(e.getY() / this.ge.getScale());
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseX = (int)(e.getX() / this.ge.getScale());
		this.mouseY = (int)(e.getY() / this.ge.getScale());
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		this.buttons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		this.buttons[e.getButton()] = false;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		this.keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		this.keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

	public int getMouseX(){return this.mouseX;}
	public int getMouseY(){return this.mouseY;}
}




class Instructions extends Thing {
    public Button b = new Button(30, 320, 200, 30, "Exit");

    public Font font = ResourceLoader.mainFont.deriveFont(30f);
    public int yOff = 30;

    public Instructions(GameEngine ge) {
        this.b.onClick = (t, u) -> {
            ge.getThings().remove(this);
            ge.showInstructions = false;
        };
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ge.getWidth(), ge.getWidth());

        g.setColor(Color.WHITE);
        g.setFont(this.font);

        g.drawString("WAD to move", 30, 30 + this.yOff);
        g.drawString("Space to Jump", 30, 70 + this.yOff);
        g.drawString("Shift to dash", 30, 110 + this.yOff);
        g.drawString("S to crouch", 30, 150 + this.yOff);
        g.drawString("Press & hold left mouse button to begin charging arrow shot", 30, 190 + this.yOff);
        g.drawString("The longer you hold, the farther it goes", 30, 230 + this.yOff);
        g.drawString("Release the mouse button to fire", 30, 270 + this.yOff);

        b.draw(g, ge);
    }
}


class PatrolEnemy extends Enemy {
    public int patrolFor = 100;
    public int currentPatrolVal = patrolFor;
    public int patrolSpeed = 5;

    public PatrolEnemy(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.dx = this.patrolSpeed;
        this.height = this.width = 50;
        this.color = Color.PINK;
        this.healthBarWidth = this.width - 20;
    }

    @Override 
    public void update(GameEngine ge) {
        this.prevX = this.x;
        this.prevY = this.y;
        if (this.dead)
            return;
        this.dy += Constants.GRAVITY;
        this.x += this.dx;
        this.y += this.dy;

        this.currentPatrolVal--;
        if (this.currentPatrolVal == 0) {
            this.currentPatrolVal = this.patrolFor;
            this.dx = -this.patrolSpeed;
            this.patrolSpeed = -this.patrolSpeed;
        }
        if (this.health <= 0) {
            this.dead = true;
            this.dx = 0;
            this.dy = 0;
        }
    }
}








class Platform extends Thing {
    public BufferedImage img;
    
    public Platform(double x, double y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.dx = 0;
        this.dy = 0;
        this.width = 80;
        this.height = 10;
        this.collisionID = Collision.CollisionsType.GROUND;
        try {
            this.img = ImageIO.read(new File("image/platformm.png"));
        } catch (IOException e) {
            System.out.println("There was an error reading image image/platform.png");
        }
        this.img.getWidth();
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(this.img, null, (int)this.x, (int)this.y);
    }
}
class Player extends Thing {
    public boolean facingRight = true;
    public boolean dashing = false;
    public boolean crouching = false;
    public boolean firing = false;
    
    public double movementSpeed = 10;
    public double maxDashSpeed = 20;
    public double dashSpeed = 20;
    public double dashReduction = 1;

    public Arrow currentArrow = null;
    public double currentArrowPower = 0;

    public double health = 100;
    public double maxHealth = 100;

    public double dashCooldown = 50.0;
    public double currentDashCooldown = 0;
    public double dashCooldownReduction = 2;

    public double iframe = 50;

    public Color fadeColor;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.width = 60;
        this.height = 60;
        this.color = Color.RED;
        this.fadeColor = new Color(255, 0, 0, 150);
        this.collisionID = Collision.CollisionsType.PLAYER;
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
    }

    @Override 
    public void update(GameEngine ge) {
        this.prevX = this.x;  
        this.prevY = this.y;
        if (this.currentDashCooldown != 0) {
            this.currentDashCooldown -= this.dashCooldownReduction;
        }
        if (this.currentArrow != null) {
            this.currentArrow.x = this.x;
            this.currentArrow.y = this.y;
            this.setRotationalForce(ge);
        }
        if (ge.getInput().isButton(MouseEvent.BUTTON1) && !this.firing)
            this.beginFiringArrow(ge);
        if (!ge.getInput().isButton(MouseEvent.BUTTON1) && this.firing)
            this.releaseArrow(ge);
        if (this.firing && this.currentArrowPower < Constants.MAX_ARROW_FORCE)
            this.currentArrowPower += Constants.ARROW_FORCE;
        this.dashLogic(ge);
        if (ge.getInput().isKeyDown(KeyEvent.VK_S))
            this.dy += Constants.GRAVITY;
        if (ge.getInput().isKeyDown(KeyEvent.VK_A))
            this.moveLeft(ge);
        if (ge.getInput().isKeyDown(KeyEvent.VK_D))
            this.moveRight(ge);
        if (ge.getInput().isKeyDown(KeyEvent.VK_SPACE) && this.onground)
            this.jump();
        if (ge.getInput().isKeyDown(KeyEvent.VK_SHIFT) && !this.dashing && this.currentDashCooldown == 0 && !this.crouching)
            this.dash();
        if (ge.getInput().isKeyDown(KeyEvent.VK_S) && !this.crouching && !this.dashing)
            this.crouchDown();
        if (this.crouching && !ge.getInput().isKeyDown(KeyEvent.VK_S))
            this.crouchUp();
        if (this.y + this.height > ge.getHeight()) {
            this.y = ge.getHeight() - this.height;
            this.onground = true;
        }
        this.applyForces();
        if (this.iframe > 0)
            this.iframe--;
    }

    public void beginFiringArrow(GameEngine ge) {
        this.currentArrow = new Arrow(this.x, this.y, 0, 0);
        this.currentArrow.cameraAffect = false;
        ge.getThings().add(currentArrow);
        this.firing = true;
    }

    public void setRotationalForce(GameEngine ge) {
        double dx = ge.getInput().getMouseX() - this.x;
        double dy = ge.getInput().getMouseY() - this.y;
        double angle = Math.atan2(dy, dx) + Math.toRadians(90);
        double forceX = Math.toDegrees(Math.sin(angle)) * this.currentArrowPower;
        double forceY = Math.toDegrees(Math.cos(angle)) * this.currentArrowPower;
        this.currentArrow.dx = forceX;
        this.currentArrow.dy = -forceY;
    }

    public void releaseArrow(GameEngine ge) {
        if (this.currentArrowPower >= Constants.MIN_ARROW_FORCE) {
            this.setRotationalForce(ge);
            this.currentArrow.update = true;
            this.currentArrow.cameraAffect = true;
            this.currentArrow.currentlyFiring = false;
        } else {
            GameEngine.arrows--;
            ge.getThings().remove(this.currentArrow);
        }
        this.currentArrow = null;
        this.firing = false;
        this.currentArrowPower = 0;
    }

    public void dashLogic(GameEngine ge) {
        if (this.dashing && this.dashSpeed <= 0) {
            this.dashing = false;
            this.movementSpeed = 10;
            this.dashSpeed = this.maxDashSpeed;
            this.currentDashCooldown = this.dashCooldown;
        }
        if (this.dashing) {
            this.dashSpeed -= this.dashReduction / (this.crouching ? 1 : 2);
            this.dx = this.facingRight ? this.dashSpeed : -this.dashSpeed;
        }
    }

    public void applyForces() {
        this.dy += Constants.GRAVITY;
        this.x += this.dx;
        this.y += this.dy;
    }

    public void moveLeft(GameEngine ge) {
        this.x -=  this.movementSpeed / (this.crouching ? 2 : 1);
        if (!this.dashing)
            this.facingRight = false;
    }

    public void moveRight(GameEngine ge) {
        this.x += this.movementSpeed / (this.crouching ? 2 : 1);
        if (!this.dashing)
            this.facingRight = true;
    }

    public void jump() {
        this.dy = Constants.JUMP;
        this.onground = false;
    }

    public void dash() {
        this.dx = this.facingRight ? this.dashSpeed : -this.dashSpeed;
        this.dashing = true;
        this.movementSpeed = 5;
    }

    public void crouchDown() {
        this.crouching = true;
        this.height /= 2;
        this.y += this.height;
        this.maxDashSpeed /= 2;
        this.prevY = this.y;
    }

    public void crouchUp() {
        this.crouching = false;
        this.y -= this.height;
        this.height *= 2;
        this.maxDashSpeed *= 2;
        this.prevY = this.y;
    }

    public void changeHealth(int by, GameEngine ge) {
        if (this.iframe > 0 && by < 0)
            return;
        this.health += by;
        if (this.health <= 0)
            ge.gameOver = true;
    }
}
class ResourceLoader {
    public static Font mainFont;

    public static void loadFonts() {
        try {
            mainFont = Font.createFont(Font.TRUETYPE_FONT, new File("font\\CaviarDreams.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mainFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
}
class Setup {
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
class Text extends Thing {
    public String text;

    public Font font = ResourceLoader.mainFont.deriveFont(30f);

    public Text(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.height = 5000;
        this.width = 5000;
        this.dx = 0;
        this.dy = 0;
        this.text = text;
    }

    @Override 
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.WHITE);
        g.setFont(this.font);
        g.drawString(this.text, (int)this.x, (int)this.y);
    }
}
class Thing {
    protected double x;
    protected double y;
    protected int width;
    protected int height;

    protected Color color;

    protected double dx;
    protected double dy;

    protected Collision.CollisionsType collisionID;

    public boolean cameraAffect = true;
    public boolean onground = false;
    public boolean stopMovingOnCollision = false;
    public boolean checkCollision = true;

    public double prevX;
    public double prevY;

    public void draw(Graphics g, GameEngine ge) {}
    public void update(GameEngine ge) {}
    public void bounce() {}

    public double getCenterX() {
        return this.width / 2;
    }

    public double getCenterY() {
        return this.height / 2;
    }
    
    public boolean collides(Thing other) {
        return this.x < other.x + other.width &&
               this.y < other.y + other.height &&
               this.x + this.width > other.x &&
               this.y + this.height > other.y;
    }

    public Thing getCollisionOverlap(Thing other) {
        double[] horizontal = {this.x, this.x + this.width, other.x, other.x + other.width};
        double[] vertical = {this.y + this.height, this.y, other.y + other.height, other.y};
        Arrays.sort(horizontal);
        Arrays.sort(vertical);
        Thing retValue = new Thing();
        retValue.x = horizontal[1];
        retValue.y = vertical[1];
        retValue.width = (int)horizontal[2] - (int)retValue.x;
        retValue.height = (int)vertical[2] - (int)retValue.y;
        return retValue;
    }

    public static boolean isInFrame(Thing t, GameEngine ge) {
        final Camera c = ge.getCamera();
        return t.x - 100 < c.x + ge.getWidth() && t.x + t.width + 100 > c.y && t.y - 100 < c.y + ge.getHeight() && t.y + t.height + 100 > c.y;
    }
}
class Time {
    public static double getNanoSeconds() {
        return System.nanoTime() / 1000000000.0;
    }
}

class Window {
    private JFrame frame;
    private Canvas canvas;
    private BufferedImage image;
    private Graphics graphics;
    private BufferStrategy bs;
    private GameEngine game;

    public Window(GameEngine ge) {
        this.game = ge;
        this.image = new BufferedImage(ge.getWidth(), ge.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.canvas = new Canvas();
        Dimension dimension = new Dimension(
            (int)(ge.getWidth() * ge.getScale()), 
            (int)(ge.getHeight() * ge.getScale())
        );
        this.canvas.setPreferredSize(dimension);
        this.frame = new JFrame(ge.getTitle());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.canvas, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        this.canvas.requestFocus();
        this.canvas.createBufferStrategy(2);

        this.bs = this.canvas.getBufferStrategy();
        this.graphics = this.bs.getDrawGraphics();
    }

    public void update() {
        this.graphics.drawImage(this.image, 0, 0, this.canvas.getWidth(), this.canvas.getHeight(), null);
        this.game.update(this.graphics);
        this.bs.show();
    }

    public JFrame getFrame()    { return this.frame; }
    public Canvas getCanvas()   { return this.canvas; }
    public BufferedImage getImage()    { return this.image; }
    public Graphics getGraphics() { return this.graphics; }
    public BufferStrategy getBs()       { return this.bs; }
}
