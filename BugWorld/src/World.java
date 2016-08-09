
import java.util.ArrayList;
import java.util.List;
// import java.util.Scanner;

public class World {
	
	private List<Bug> bugs;
	private List<Plant> plants;
	private ArrayList<Obstacle> obstacles;
	
	private int worldHeight;
	private int worldWidth;

	public World(int width, int height) {
		this.bugs = new ArrayList<Bug>();
		this.plants = new ArrayList<Plant>();
		this.obstacles = new ArrayList<Obstacle>();
		this.worldHeight = height;
		this.worldWidth = width;

		// set up text based menu
		/*
		 * Scanner scan = new Scanner(System.in);
		 * 
		 * System.out.println("How many times do you want world to refresh?: ");
		 * int numRefreshes = scan.nextInt();
		 * 
		 * System.out.println("Enter number of bugs (max 20): "); int numBugs =
		 * scan.nextInt(); if (numBugs < 0) { numBugs = 0; } if (numBugs > 20) {
		 * numBugs = 20; }
		 * 
		 * System.out.println("Enter number of plants: "); int numPlants =
		 * scan.nextInt(); if (numPlants < 0) { numPlants = 0; }
		 * 
		 * System.out.println("Enter number of obstacles: "); int numObstacles =
		 * scan.nextInt();
		 * 
		 * System.out.println("Enter world width: "); int worldWidth =
		 * scan.nextInt(); this.width = worldWidth;
		 * 
		 * System.out.println("Enter world height: "); int worldHeight =
		 * scan.nextInt(); this.height = worldHeight;
		 * 
		 * scan.close();
		 */

		int numBugs = 15;
		int numPlants = 40;
		int numObstacles = 20;

		/*
		 * PRINT VERSION // create bugs (with random positions), add to bugs
		 * ArrayList for (int i = 0; i < numBugs; i++) { int x = 1 +
		 * (int)(Math.random() * worldWidth); int y = 1 + (int)(Math.random() *
		 * worldHeight); char bugSymbol = symbols[i];
		 * 
		 * Bug bug = new Bug("ant", "Bob", bugSymbol, x, y, 50, 123, 2);
		 * this.bugs.add(bug); }
		 */

		// make approx equal number of each type of bug (general, crawling,
		// jumping, flying)
		
		int curNumBugs = 0;
		while (curNumBugs < numBugs) {
			addObject('B');
			curNumBugs++;

			if (curNumBugs < numBugs) {
				addObject('C');
				curNumBugs++;
			}

			if (curNumBugs < numBugs) {
				addObject('J');
				curNumBugs++;
			}

			if (curNumBugs < numBugs) {
				addObject('F');
				curNumBugs++;
			}

		}

		// create plants (all start at size 100) at random positions
		for (int i = 0; i < numPlants; i++) {
			addObject('P');
		}

		// create obstacles at random positions
		for (int i = 0; i < numObstacles; i++) {
			addObject('O');
		}

		this.drawWorld();

		// refresh world specified number of times
		/*
		 * for (int i = 0; i < numRefreshes; i++) { this.updateWorld();
		 * this.drawWorld(); }
		 */

	}

	/*
	 * draws bugs, plants and obstacles in world
	 */
	public void drawWorld() {
		// draw top border
		System.out.print("|");
		for (int i = 0; i < this.worldWidth; i++) {
			System.out.print("-");
		}
		System.out.println("|");

		// draw grid contents
		for (int row = 1; row < this.worldHeight + 1; row++) {
			System.out.print("|");
			for (int col = 1; col < worldWidth + 1; col++) {
				String letter = " ";
				// check if there is a bug at current position
				for (Bug b : this.bugs) {

					if (b.getX() == col && b.getY() == row) {
						letter = b.getSymbol() + "";
					}

				}
				// check if there is a plant at current position
				for (Plant p : this.plants) {
					if (p.getX() == col && p.getY() == row) {
						letter = p.getSize() + "";
					}
				}

				// check if there is an obstacle at current position
				for (Obstacle o : this.obstacles) {
					if (o.getX() == col && o.getY() == row) {
						letter = "Ø";
					}
				}

				// draw symbol of object at current location
				System.out.print(letter.charAt(0));
			}
			System.out.println("|");
		}

		// draw bottom border
		System.out.print("|");
		for (int i = 0; i < worldWidth; i++) {
			System.out.print("-");
		}
		System.out.println("|");
	}

	/*
	 * Makes bugs move
	 */
	public void updateWorld() {
		// temp list which contains all the bugs that didn't die this turn
		List<Bug> livingBugs = new ArrayList<Bug>();
		
		// make each bug move
		for (Bug b : this.bugs) {
			String direction = b.smellFood(this);

			// if bug doesn't smell food, pick random direction
			if (direction.equals("none")) {

				double randNum = Math.random();

				if (randNum < 0.25) {
					direction = "N";
				} else if (randNum < 0.5) {
					direction = "S";
				} else if (randNum < 0.75) {
					direction = "E";
				} else {
					direction = "W";
				}
			}

			moveBug(b, direction);
			
			// decrease bug's energy
			// if bug's energy gets to zero, bug dies
			// (so is not included in new arrayList)
			// and a new DeadBug (at same position as bug) is added to obstacles List
			b.setEnergy(b.getEnergy() - 2);
			
			if (b.getEnergy() > 0) {
				livingBugs.add(b);
			} else {
				obstacles.add(new DeadBug(b.getX(), b.getY()));
			}
			
		}
		
		// set temp arrayList as bugs ArrayList
		this.bugs = livingBugs;

		// remove level 0 plants
		List<Plant> livingPlants = new ArrayList<Plant>();
		for (Plant p: this.plants) {
			if (p.getSize() > 0) {
				livingPlants.add(p);
				if (p.getSize() == 100) {
					int randX = 1 + (int) (Math.random() * worldWidth);
					int randY = 1 + (int) (Math.random() * worldHeight);
					
					while (!cellEmpty(randX, randY)) {
						randX = 1 + (int) (Math.random() * worldWidth);
						randY = 1 + (int) (Math.random() * worldHeight);
					}
					
					livingPlants.add(new Plant(50, randX, randY));
				}
				
			}
		}
		
		this.plants = livingPlants;
		
		/*int randX = 0;
		int randY = 0;
		
		// ensure there are always at least 10 plants
		while (this.plants.size() < 10) {
			randX = 1 + (int) (Math.random() * worldWidth);
			randY = 1 + (int) (Math.random() * worldHeight);
			plants.add(new Plant(50, randX, randY));
		} */
		
		// make plants grow
		for (Plant p : this.plants) {
			p.grow();
		}

	}

	/*
	 * moves the bug in specified direction, if the space there is free if there
	 * is a plant there, bug eats the plant
	 */
	public void moveBug(Bug b, String direction) {
		// get bug's location
		int bugX = b.getX();
		int bugY = b.getY();
		int bugNewX = 1;
		int bugNewY = 1;

		// north
		if (direction.equals("N")) {
			bugNewX = bugX;
			bugNewY = bugY - 1;

		// south
		} else if (direction.equals("S")) {
			bugNewX = bugX;
			bugNewY = bugY + 1;

		// east
		} else if (direction.equals("E")) {
			bugNewX = bugX + 1;
			bugNewY = bugY;

		// west
		} else {
			bugNewX = bugX - 1;
			bugNewY = bugY;
		}

		// make sure bugs don't move to where another plant, bug, or obstacle is
		// if there is a plant, bug eats plant
		if (getPlantAt(bugNewX, bugNewY) != null) {
			b.eatPlant(getPlantAt(bugNewX, bugNewY));
			// if no other bug or obstacle is there, bug moves
		} else if (getBugAt(bugNewX, bugNewY) == null && !obstacleAt(bugNewX, bugNewY)) {
			b.move(direction, 1, worldWidth, worldHeight);
		}

	}

	/*
	 * returns the bug which is at the specified location
	 */
	public Bug getBugAt(int x, int y) {
		Bug bugAt = null;

		for (Bug b : this.bugs) {
			if (b.getX() == x && b.getY() == y) {
				bugAt = b;
			}
		}

		return bugAt;
	}

	/*
	 * returns whether there is an obstacle at the specified location
	 */
	public boolean obstacleAt(int x, int y) {
		for (Obstacle o : this.obstacles) {
			if (o.getX() == x && o.getY() == y) {
				return true;
			}
		}
		return false;
	}

	/*
	 * returns the plant which as at the specified location
	 */
	public Plant getPlantAt(int x, int y) {
		Plant plantAt = null;

		for (Plant p : this.plants) {
			if (p.getX() == x && p.getY() == y) {
				plantAt = p;
			}
		}

		return plantAt;
	}

	// getters
	public List<Bug> getBugs() {
		return this.bugs;
	}

	public List<Plant> getPlants() {
		return this.plants;
	}

	public ArrayList<Obstacle> getObstacles() {
		return this.obstacles;
	}
	
	public List<BugWorldObject> getAllObjects() {
		List<BugWorldObject> objects = new ArrayList<BugWorldObject>();
		objects.addAll(bugs);
		objects.addAll(obstacles);
		objects.addAll(plants);
		
		return objects;
	}
	
	public boolean cellEmpty(int x, int y) {
		List <BugWorldObject> objects = getAllObjects();
		
		for (BugWorldObject o: objects) {
			if (o.getX() == x && o.getY() == y) {
				return false;
			}
		}
		
		return true;
	}
	
	public void addObject(char objectSymbol) {
		if (getAllObjects().size() >= (worldWidth * worldHeight)) {
			return;
		}
		
		int randX = 1 + (int) (Math.random() * worldWidth);
		int randY = 1 + (int) (Math.random() * worldHeight);

		while (!cellEmpty(randX, randY)) {
			randX = 1 + (int) (Math.random() * worldWidth);
			randY = 1 + (int) (Math.random() * worldHeight);
		}

		
		switch (objectSymbol) {
		case 'B':
			bugs.add(new Bug(randX, randY));
			break;
			
		case 'C':
			bugs.add(new CrawlingBug(randX, randY));
			break;
			
		case 'J':
			bugs.add(new JumpingBug(randX, randY));
			break;
			
		case 'F':
			bugs.add(new FlyingBug(randX, randY));
			break;
			
		case 'O':
			obstacles.add(new Obstacle(randX, randY));
			break;
			
		case 'P':
			plants.add(new Plant(50, randX, randY));
			break;
		}
		
		
	}
}
