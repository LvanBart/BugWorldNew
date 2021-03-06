
public class BugWorldObject {
	protected char symbol;
	protected int x;
	protected int y;
	
	public BugWorldObject() {
		this.symbol = '/';
		this.x = 100;
		this.y = 100;
	}
	
	public BugWorldObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
