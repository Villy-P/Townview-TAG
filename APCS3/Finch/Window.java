import javax.swing.JFrame;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Canvas;

public class Window {
    private JFrame frame;
    private Canvas canvas;
    private BufferedImage image;
    private Graphics graphics;
    private BufferStrategy bs;
    private GameEngine game;

    public Window(GameEngine ge) {
        this.game = ge;
        this.image = new BufferedImage(GameEngine.width, GameEngine.height, BufferedImage.TYPE_INT_RGB);
        this.canvas = new Canvas();
        Dimension dimension = new Dimension(
            (int)(GameEngine.width * ge.getScale()), 
            (int)(GameEngine.height * ge.getScale())
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

    public JFrame         getFrame()    { return this.frame; }
    public Canvas         getCanvas()   { return this.canvas; }
    public BufferedImage  getImage()    { return this.image; }
    public Graphics       getGraphics() { return this.graphics; }
    public BufferStrategy getBs()       { return this.bs; }
}