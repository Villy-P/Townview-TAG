public class Camera {
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
