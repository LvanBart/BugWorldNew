
public class JumpingBug extends Bug {
	
	public JumpingBug () {
		super();
		this.species = "grasshopper";
		this.symbol = 'J';
		this.smellRange = 3;
	}
	
	public JumpingBug(String species, String name, char symbol, int x, int y, int energy, int id) {
		super(species, name, symbol, x, y, energy, id, 3);
	}
	
	
}

