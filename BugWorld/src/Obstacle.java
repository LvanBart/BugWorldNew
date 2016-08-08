
public class Obstacle {
	private int x;
	private int y;

	public Obstacle() {
		this.x = 1;
		this.y = 1;
	}

	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// getters
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	// setters
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
