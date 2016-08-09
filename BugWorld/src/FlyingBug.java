
public class FlyingBug extends Bug {

	public FlyingBug () {
		super();
		this.species = "fly";
		this.symbol = 'F';
		this.smellRange = 4;
		
	}
	
	public FlyingBug(String species, String name, int x, int y, int energy, int id) {
		super(species, name, x, y, energy, id, 4);
		this.setSymbol('F');
	}
}
