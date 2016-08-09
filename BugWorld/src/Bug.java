
import java.util.Scanner;


public class Bug extends BugWorldObject {

	protected String species;
	protected String name;
	protected int energy;
	protected int id;
	protected int smellRange;

	// constructors

	// default constructor
	public Bug() {
		this.species = "ant";
		this.name = "Gerald";
		this.symbol = 'B';
		this.x = 1;
		this.y = 1;
		this.energy = 100;
		this.id = 12345;
		this.smellRange = 2;

	}
	
	public Bug(int x, int y) {
		super(x, y);
		this.species = "ant";
		this.name = "Gerald";
		this.symbol = 'B';
		this.energy = 100;
		this.id = 12345;
		this.smellRange = 2;
	}

	public Bug(String species, String name, int x, int y, int energy, int id, int smellRange) {
		this.species = species;
		this.name = name;
		this.symbol = 'B';
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.id = id;
		this.smellRange = smellRange;

	}

	public String toString() {
		return "species: the bug's species, name: The bug's name, symbol: a character which represents the bug , "
				+ "x: The bug's horizontal position, y: the bug's vertical position, energy: how much energy the bug has left"
				+ ", id: a unique combination of digits which identifies the bug ";
	}

	public String toText() {
		return this.species + " " + this.name + " " + this.symbol + " " + this.x + " " + this.y + " " + this.energy
				+ " " + " " + this.id;
	}

	// getters

	public String getSpecies() {
		return this.species;
	}

	public String getName() {
		return this.name;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getEnergy() {
		return this.energy;
	}

	public int getId() {
		return this.id;
	}


	// setters

	public void setSpecies(String species) {
		this.species = species;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setId(int id) {
		this.id = id;
	}


	// other methods

	/*
	 * Moves bug specified distance in specified direction. If bug moves out of
	 * bounds, positions bug at edge of border
	 */
	public void move(String direction, int distance, int maxX, int maxY) {

		if (direction.equals("N")) {
			this.y -= distance;
			if (this.y < 1) {
				this.y = 1;
			}
		} else if (direction.equals("S")) {
			this.y += distance;
			if (this.y > maxY) {
				this.y = maxY;
			}
		} else if (direction.equals("E")) {
			this.x += distance;
			if (this.x > maxX) {
				this.x = maxX;
			}
		} else if (direction.equals("W")) {
			this.x -= distance;
			if (this.x < 1) {
				this.x = 1;
			}

		}


	}

	/**
	 * Prompts the user for attributes, then sets bug's attributes to the
	 * provided values
	 */

	public void setAllAttributes() {
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter a species: ");
		String newSpecies = scan.next();
		this.species = newSpecies;

		System.out.println("Enter a name: ");
		String newName = scan.next();
		this.name = newName;

		System.out.println("Enter a symbol: ");
		char newSymbol = scan.next().charAt(0);
		this.symbol = newSymbol;

		System.out.println("Enter x coordinate (integer): ");
		int newX = scan.nextInt();
		this.x = newX;

		System.out.println("Enter y coordinate (integer): ");
		int newY = scan.nextInt();
		this.y = newY;

		System.out.println("Enter energy level (integer): ");
		int newEnergy = scan.nextInt();
		this.energy = newEnergy;

		System.out.println("Enter id (integer): ");
		int newId = scan.nextInt();
		this.id = newId;

		scan.close();

	}

	/* returns the direction in which there is food within the specified range of the bug
	 * if the bug doesn't smell food, returns "none"
	 * priority is given to North, then South, then East, then West
	 * bugs do not smell plants which are size 0
	 */
	public String smellFood(World world) {

		for (int curDistance = 1; curDistance <= this.smellRange; curDistance++ ) {
			int curX = 1;
			int curY = 1;

			// north
			curX = this.x;
			curY = this.y - curDistance;

			if (world.getPlantAt(curX, curY) != null && world.getPlantAt(curX, curY).getSize() > 0) {
				return "N";
			}

			// south
			curX = this.x;
			curY = this.y + curDistance;

			if (world.getPlantAt(curX, curY) != null && world.getPlantAt(curX, curY).getSize() > 0) {
				return "S";
			}

			// east
			curX = this.x + curDistance;
			curY = this.y;

			if (world.getPlantAt(curX, curY) != null && world.getPlantAt(curX, curY).getSize() > 0) {
				return "E";
			}

			// west
			curX = this.x - curDistance;
			curY = this.y;

			if (world.getPlantAt(curX, curY) != null && world.getPlantAt(curX, curY).getSize() > 0) {
				return "W";
			}
		}


		return "none";
	}
	
	public void eatPlant(Plant plant) {
		if (plant.getSize() == 0 || this.energy >= 100) {
			return;
		}
		
		this.energy += 10;
		if (this.energy > 100) {
			this.energy = 100;
		}
		
		plant.shrink();
	}

}
