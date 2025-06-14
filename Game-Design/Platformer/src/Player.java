import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends Thing {
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
