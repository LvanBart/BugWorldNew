
public class Plant {
	private int size;
	private int x;
	private int y;

	public Plant() {
		this.size = 0;
		this.x = 1;
		this.y = 1;
	}

	public Plant(int size, int x, int y) {
		if (size < 0) {
			this.size = 0;
		} else if (size > 9) {
			this.size = 9;
		} else {
			this.size = size;
		}

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

	public int getSize() {
		return this.size;
	}

	// causes plant's size to increase by 1, to a maximum of 9
	public void grow () {
		if (this.size < 9) {
			this.size ++;
		}
	}
	
	// causes plant's size to decrease by 2, to a minimum of 0
	public void shrink () {
		if (this.size > 0) {
			this.size -= 2;
		}
	}

}
