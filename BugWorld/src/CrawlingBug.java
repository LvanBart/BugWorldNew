
public class CrawlingBug extends Bug {
	
	public CrawlingBug() {
		super();
		this.species = "cockroach";
		this.symbol = 'C';
	}
	
	public CrawlingBug(String species, String name, int x, int y, int energy, int id) {
		super(species, name, x, y, energy, id, 2);
		this.setSymbol('C');
	}
	
}
