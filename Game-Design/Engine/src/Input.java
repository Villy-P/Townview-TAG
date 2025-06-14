import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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