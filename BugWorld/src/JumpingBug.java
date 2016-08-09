
public class JumpingBug extends Bug {
	
	public JumpingBug () {
		super();
		this.species = "grasshopper";
		this.symbol = 'J';
		this.smellRange = 4;
	}
	
	public JumpingBug(int x, int y) {
		super(x, y);
		this.symbol = 'J';
		this.species = "grasshopper";
		this.smellRange = 4;
	}
	
	public JumpingBug(String species, String name, int x, int y, int energy, int id) {
		super(species, name, x, y, energy, id, 3);
		this.setSymbol('J');
	}
	
	
}

