public class End extends Thing {
    public End(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.collisionID = Collision.CollisionsType.END;
    }
}
