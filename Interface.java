// Name: Nicholas Worthington
// Student #: C3307819
//
//
//

import java.util.Scanner;

public class Interface {
	
	final private static int MAX_STARS = 2;
	private Star[] stars = new Star[MAX_STARS];
	//private int totalStars = 0;
	private static Scanner console = new Scanner(System.in);
	
	private void run(){
		
		//Scanner console = new Scanner(System.in);
		
		int end = 1; // variable for user to quit program, will not loop menu if 0
		int skipQuit = 0; // if 1, skips default quit sequence
		for(int i = 0; i<MAX_STARS; i++) { // fills array with blank star objects
			stars[i] = new Star();
		}
			
		do {
			
			String name, sType, orbitedStar; // variables holding user input for use in actions
			double ra = 0, dec = 0;
			int loop; // variable used to determine running of various while loops
			double angDistance;
			
			showMenu();
			int action = console.nextInt(); // stores users choice of action
			console.nextLine();
			
			
			// OPERATIONS FOR CHOSEN ACTION
			switch(action) {
				case 1: // Add a star
					
					// returns an error and exits to menu if max stars already exist in database
					if(atMaxStars()) break;
					
					System.out.println("\nAdding a star...\n");
					System.out.println("When adding a star, note that right ascension ranges from 0 to 360,"
							+ " declination from -90 to 90,"
							+ "	and spectral type is a combination of a letter (OBAFGKM) followed by a number (0-9)");
					
					// prompts user for star's name
					name = inputStarName(1);
					// prompts user for star's right ascension
					ra = inputStarRa();
					// prompts user for star's declination
					dec = inputStarDec();
					// prompts user for star's spectral type
					sType = inputStarSType();
					
					// creates star using user input
					createStar(name, ra, dec, sType);
					
					break;
					
				case 2: // Add a planet 
					
					// returns error and exits to menu if no stars in the database to add a planet to
					if(noStars()) break;
					// returns error and exits to menu if there is no room for a planet in the database
					if(!roomForPlanet()) break;
				
					
					System.out.println("\nAdding a planet...\n");
					
					// Orbited star
					
					int orbitedStarInt = inputOrbitedStar();
					if (stars[orbitedStarInt].atMaxPlanets()) break;
					// prompts user for planet's name
					name = inputPlanetName(1, orbitedStarInt);
					if(stars[orbitedStarInt].planetNameExists(name)) break;
					// prompts user for planet's right ascension
					ra = inputPlanetRa();
					// prompts user for planet's declination
					dec = inputPlanetDec();
					
					// adding the planet
					stars[orbitedStarInt].createPlanet(name, ra, dec);
					
					System.out.println("Planet added!");
					
					break;
					
					
				case 3: // Delete a star
					// returns error and exits to menu if no stars in the database
					if(noStars()) break;
					
					System.out.println("\nDeleting a star...\n");
					
					listStars();
					
					name = inputStarName(2);
					
					deleteStar(name);
						
					break;
					
				case 4: //delete planet
					
					if(noStars()) break;
					if(noPlanets()) break;
					
					System.out.println("\nDeleting a planet...\n");
					
					listStars();
					
					System.out.println("Please enter star around which planet orbits:");
					orbitedStar = inputStarName(2);
					orbitedStarInt = inputStarNameMatches(orbitedStar);
					
					listPlanets(orbitedStarInt);
					
					name = inputPlanetName(2, orbitedStarInt);
					
					deletePlanet(orbitedStarInt, name);
					
					break;
					
				case 5: //display list
					
					if(noStars()) break;
					System.out.println("\nDisplaying all objects in database...\n");

					
					listStars();
					for(int i = 0; i<Star.totalStars(); i++) {
						listPlanets(i);
					}
					
					break;
					
				case 6: //display planets orbiting a star
					
					if(noStars()) break;
					System.out.println("\nDisplaying planets orbitting a star...\n");
					
					listStars();
					
					name = inputStarName(2);
					int starInt = inputStarNameMatches(name);
					
					listPlanets(starInt);
					
					break;
					
				case 7: // Find angular distance between 2 objects
					String objectA, objectB;
					
					
					if(noStars()) break;
					if(onlyOneObject()) {
						System.out.println("Must be at least two objects in the database. Please add more.");
						break;
					}
					
					System.out.println("Ang. distance between which two objects");
					
					do {
						loop = 0;
						System.out.println("Enter first object:");
						objectA = inputObjectName(2);
						System.out.println("Enter second object:");
						objectB = inputObjectName(2);
						if(objectsAreIdentical(objectA,objectB)) loop = 1;
					} while(loop == 1);
					
					System.out.println("Angular distance: " +angularDistanceBetween(objectA, objectB));
					
					break;
					
				case 8: // Find objects within a set angular distance of a certain object 
					double range;
					
					if(noStars()) break;
					if(onlyOneObject()) {
						System.out.println("Must be at least two objects in the database. Please add more.");
						break;
					}
					
					System.out.println("\nFinding objects within a set angular distance of an object...\n");
					
					loop = 0;
					System.out.println("Which object do you want to measure from?");
					listStars();
					listAllPlanets();
					objectA = inputObjectName(2);
					
					System.out.println("What distance do you want to measure between?");
					range = inputAngularDistance();
					
					listObjectsWithinDistance(objectA, range);
					
					break;
				default: // Quit program (or no valid option chosen)
					System.out.print("Are you sure you want to quit? This will erase all database entries.");
					skipQuit = 1; // skips default quit sequence
			}
			
			if (skipQuit != 1) { // default quit sequence after any action completes
				System.out.println("\nReturn to menu?  (0): Yes  (1): No");
				int confirmQuit = console.nextInt();
				if (confirmQuit == 1) {
					System.out.println("Are you sure you want to quit? This will erase all database entries.  (0): Yes  (1): No"); //prompts user to quit program
					end = console.nextInt();
				}
			} else { // quit sequence when quit action selected from menu
				System.out.println("   (0): Yes  (1): No"); //prompts user to quit program
				end = console.nextInt();
				skipQuit = 0; // returns to using default quit sequence in case quit program action was backed out of
			}
			
		} while (end!=0); //if end = 0 then program will quit
		
		System.out.println("Goodbye!");
		console.close();
	}
	
	public static void main(String[] args) {
		Interface intFace = new Interface();
		intFace.run();
	}
	
	public static void showMenu() {
		// menu of actions for user to pick
					System.out.println("*********************************************");
					System.out.println("Welcome to your astronomical object database.");
					System.out.println("*********************************************");
					System.out.println("Please choose an action:");
					System.out.println("1 - Add a star");
					System.out.println("2 - Add a planet");
					System.out.println("3 - Delete a star");
					System.out.println("4 - Delete a planet");
					System.out.println("5 - Display list of all objects");
					System.out.println("6 - Display list of planets orbitting a star");
					System.out.println("7 - Find angular distance between 2 objects");
					System.out.println("8 - Find objects within a set angular distance of a certain object");
					System.out.println();
					System.out.println("9 - Quit program\n");
	}
	
	// Checks whether a given right ascension (ra) is within the valid range 0 <= ra <= 360
	// Returns true if valid, false otherwise.
	public static boolean raWithinRange(double ra) {
		return (ra >= 0 && ra <= 360);
	}
		
	// Checks whether a given declination (dec) is within the valid range -90 <= dec <= 90
	// Returns true if valid, false otherwise.
	public static boolean decWithinRange(double dec) {
		return (dec >= (-90) && dec <= (90));
	}
	
	// Checks whether a given angular distance (angDist) is within the valid range 0 <= angDist <= 180
	// Returns true if valid, false otherwise.
	public static boolean angDistWithinRange(double angDist) {
		return (angDist >= 0 && angDist <= 180);
	}
	
	// Gets the angular distance between two objects in the database
	// Takes right ascension and declination of both objects in degrees as input
	// Returns angular distance in degree
	public static double getAngularDistance(double ra, double ra2, double dec, double dec2) {
		double raRad = Math.toRadians(ra);
		double decRad = Math.toRadians(dec);
		double ra2Rad = Math.toRadians(ra2);
		double dec2Rad = Math.toRadians(dec2);
		
		return Math.toDegrees(Math.acos(Math.cos(raRad - ra2Rad)*Math.cos(decRad)*Math.cos(dec2Rad)+(Math.sin(decRad)*Math.sin(dec2Rad))));
	}
	
	// returns true if at max stars, false if there is room for at least one star
	public boolean atMaxStars() {
		if (Star.totalStars() == MAX_STARS) {
			System.out.println("The database is full of stars! Please delete one and try again."); 
		}
		return (Star.totalStars() == MAX_STARS);
	}
	
	// returns true if no stars in database, false if at least one star exists
	public boolean noStars() {
		if (Star.totalStars() == 0) {
			System.out.println("No astronomical objects in the database! Please add one first.");
		}
		return (Star.totalStars() == 0);
	}
	
	//returns true if no planets are found orbiting any star, false if at least one found
	public boolean noPlanets() {
		for(int i = 0; i<Star.totalStars(); i++) {
			if(stars[i].totalPlanets() != 0) return false; 
		}
		return true;
	}
	
	// returns true if only 1 object (therefore only 1 star) exists in database, returns false if none or more than one object exists
	public boolean onlyOneObject() {
		return((Star.totalStars() == 1) && noPlanets());
	}
	
	// takes names of two objects and compares them, returns true if objects have the same name (i.e. same objects) and false if names are different
	public boolean objectsAreIdentical(String objectA, String objectB) {
		if(objectA.equals(objectB)) {
			System.out.println("You cannot enter the same object twice!");
			return true;
		}
		return false;
	}
	
	// takes and returns users input object name, option 1 will print error and loop prompt if 
	// the object already exists, option 2 will quit loop if object name exists
	public String inputObjectName(int option) {
		int loop;
		int objectIndex;
		String name = "";
		do {
			objectIndex = (-1);
			loop = 0;
			System.out.println("Object name: ");
			name = console.nextLine().toLowerCase(); // stores user input of star name
			if (name.isEmpty()) {
				System.out.println("Object name must not be blank!"); // error check, if user enters a blank name
				loop = 1;
				continue;
			}
			
			for(int i = 0; i<Star.totalStars(); i++) {
				if(inputPlanetNameMatches(name, i) != (-1)) {
					objectIndex = inputPlanetNameMatches(name, i);
					break;
				}
			}
			if(objectIndex == (-1) ) {
				if(inputStarNameMatches(name) != (-1)) {
					if (option == 2) { // if deleting star, or adding planet, matching name will stop loops
						loop = 0;
					} else if (option == 1) { // if adding star, matching name will loop and print error
						System.out.println("This star already exists in the database! Please enter a different name"); // if there exists a star name, checks if it is the same as user input	
						loop = 1;
					}
				}
			} else {
				if (option == 2) { // if deleting star, or adding planet, matching name will stop loops
					loop = 0;
				} else if (option == 1) { // if adding star, matching name will loop and print error
					System.out.println("This planet already exists in the database! Please enter a different name"); // if there exists a star name, checks if it is the same as user input	
					loop = 1;
				}
			}
				
		} while (loop == 1);
		return name;
		
	}
	
	// takes and returns users input star name, option 1 (adding star) will print error and loop prompt if 
	// the star already exists, option 2 (deleting star) will quit loop if star name exists
	public String inputStarName(int option) {
		int loop;
		String name = "";
		do {
			loop = 0;
			System.out.println("Star name: ");
			name = console.nextLine().toLowerCase(); // stores user input of star name
			if (name.isEmpty()) {
				System.out.println("Star name must not be blank!"); // error check, if user enters a blank name
				loop = 1;
				continue;
			}
			
			if(inputStarNameMatches(name) != (-1)) {
				if (option == 2) { // if deleting star, or adding planet, matching name will stop loops
					loop = 0;
				} else if (option == 1) { // if adding star, matching name will loop and print error
					System.out.println("This star already exists in the database! Please enter a different name"); // if there exists a star name, checks if it is the same as user input	
					loop = 1;
				}
			}	
				
		} while (loop == 1);
		return name;
	}
	
	// takes a String star name and returns the index of star in array with matching name, 
	// returns -1 if star name not found
	public int inputStarNameMatches(String name) {
		for(int starIndex = 0; starIndex<Star.totalStars(); starIndex++) {
			if(name.equals(stars[starIndex].getName()))
				return starIndex;
		}
		return (-1);
	}
	
	// takes a String planet name and int star array index and returns the index of planet in array with matching name, 
	// returns -1 if star name not found
	public int inputPlanetNameMatches(String name, int star) {
		int nameInt;
		for(int planetIndex = 0; planetIndex<stars[star].totalPlanets(); planetIndex++) {
			nameInt = stars[star].inputPlanetNameMatches(name, planetIndex);
			if(nameInt != (-1)) return nameInt;
		}
		return (-1);
	}
		
	
	public double inputStarRa() {
		double ra;
		//Scanner console = new Scanner(System.in);
		do { 
			System.out.println("Right Ascension: ");
			ra = console.nextDouble(); // stores user input of star right ascension
			console.nextLine();
			if (!raWithinRange(ra)) 
				System.out.println("Error. Value must be between 0 and 360"); // error for invalid right ascension
		} while (!raWithinRange(ra)); // prompts user again for right ascension if not within valid range
		return ra;
	}
	
	public double inputStarDec() {
		double dec;
		do {
			System.out.println("Declination:");
			dec = console.nextDouble(); // stores user input of star declination
			console.nextLine();
			if (!decWithinRange(dec)) 
				System.out.println("Error. Value must be between -90 and 90"); // error for invalid right ascension
		} while (!decWithinRange(dec)); // prompts user again for declination if not within valid range
		return dec;
	}
	
	public String inputStarSType() {
		String sType;
		do {
			System.out.println("Spectral Type:");
			sType = console.nextLine(); // stores user input of star spectral type
			sType = sType.toUpperCase();
			if (!(sType.matches("[OBAFGKM][0-9]"))) 
				System.out.println("Error. Spectral type must be a letter (OBAFGKM) followed by a whole number (0-9)"); // error for invalid spectral type
		} while (!(sType.matches("[OBAFGKM][0-9]")));// prompts user again for spectral type if not valid
		return sType;
	}
	
	public void createStar(String name, double ra, double dec, String sType) {
		for(int i = 0; i<MAX_STARS; i++) { 
			if(!(stars[i].starExists())) { //creates a star in first empty array element
				stars[i].setStar(name, ra, dec, sType); //instantiates star
				System.out.println("\nStar added!");
				System.out.println(stars[i].getStarInfo()); //displays added star back to user
				break;
			}
		}
	}
	
	public int inputOrbitedStar() {
		int starIndex; //integer form of the input star given it matches a star (1, 2) loops while 0
		do {
			starIndex = (-1);
			//prompts user for star of which the planet orbits
			System.out.println("Which star does the planet orbit: ");
			//prints list of current stars with room for a planet
			listStarsWithRoom();
			
			//takes user input star and sets starIndex to index of star in database with matching name
			String orbitedStar  = inputStarName(2);
			starIndex = inputStarNameMatches(orbitedStar);
			
			if(starIndex != (-1)) {
				//continues loop if input star has max planets
				if(stars[starIndex].atMaxPlanets()) {
					System.out.println("Star has no room for more planets!");
					starIndex = (-1); // to continue loop
				}
			} else System.out.println("That star does not exist!");
			

		} while (starIndex == (-1));
		return starIndex;
	}
	
	
	// takes and returns users input planet name, option 1 (adding star) will print error and loop prompt if 
		// the planet already in orbit around star, option 2 (deleting star) will quit loop if planet name exists
		public String inputPlanetName(int option, int orbitedStarInt) {
			int loop;
			String name = "";
			do {
				loop = 0;
				System.out.println("Planet name: ");
				name = console.nextLine().toLowerCase(); // stores user input of star name
				if (name.isEmpty()) {
					System.out.println("Planet name must not be blank!"); // error check, if user enters a blank name
					loop = 1;
					continue;
				}
				
				if(stars[orbitedStarInt].inputPlanetNameMatches(name, orbitedStarInt) != (-1)) {
					if (option == 2) { // if deleting star, or adding planet, matching name will stop loops
						loop = 0;
					} else if (option == 1) { // if adding star, matching name will loop and print error
						System.out.println("This planet already exists in orbit of this star! Please enter a different name"); // if there exists a star name, checks if it is the same as user input	
						loop = 1;
					}
				}	
					
			} while (loop == 1);
			return name;
		}
	
	public double inputPlanetRa() {
		double ra;
		do { 
			System.out.println("Right Ascension: ");
			ra = console.nextDouble(); // stores user input of star right ascension
			console.nextLine();
			if (!raWithinRange(ra)) 
				System.out.println("Error. Value must be between 0 and 360"); // error for invalid right ascension
		} while (!raWithinRange(ra)); // prompts user again for right ascension if not within valid range
		return ra;
	}
	
	public double inputPlanetDec() {
		double dec;
		do {
			System.out.println("Declination:");
			dec = console.nextDouble(); // stores user input of star declination
			console.nextLine();
			if (!decWithinRange(dec)) 
				System.out.println("Error. Value must be between -90 and 90"); // error for invalid right ascension
		} while (!decWithinRange(dec)); // prompts user again for declination if not within valid range
		return dec;
	}
	
	public double inputAngularDistance() {
		double angDist;
		do {
			System.out.println("Angular distance:");
			angDist = console.nextDouble();
			console.nextLine();
			if(!angDistWithinRange(angDist)) 
				System.out.println("Angular distance must be between 0 and 180 degrees!");
		} while (!angDistWithinRange(angDist));
		return angDist;
	}
	
	// takes name of star as a String and deletes it, prints completion message,
	// and shifts the stars array down one index to fill gap, returns error if input star doesn't exist
	public void deleteStar(String starName) {
		int starInt = inputStarNameMatches(starName);
		if(starInt == (-1)) {
			System.out.println("Star does not exist in database");
		} else {
			stars[starInt].deleteStar();
			System.out.println("Star "+starName+" deleted!");
			// shifts each star above the deleted star down one index to fill the gap
			for(int i = starInt; i<(Star.totalStars()); i++) {
				stars[i].copyStar(stars[i+1]);
				stars[i+1].deleteStar(); // deletes the original element that was copied into stars[i], will totalStars -= 1
				Star.changeTotalStars(1); // adds 1 to totalStars to reverse the 1 removed when deleting the original element after copying
			}
			
		}
	}
	
	// takes a planets name and index of orbited star and deletes planet
	public void deletePlanet(int orbitedStarInt, String planetName) {
		int planetInt = inputPlanetNameMatches(planetName, orbitedStarInt); // finds array index of input planet name
		stars[orbitedStarInt].deletePlanet(planetInt); // deletes planet at said array index
		System.out.println("Planet " +planetName+ " deleted!");
	}
	
	// returns true if there is room to add a planet, returns false and prints error all stars at max planets
	public boolean roomForPlanet() {
		int roomForPlanet = 0;
		for(int i = 0; i<Star.totalStars(); i++) {
			if(stars[i].totalPlanets() != Star.getMaxPlanets()) {
				roomForPlanet = 1;
			}
		}
		if(roomForPlanet == 0) {
			System.out.println("There is no room for another planet in the database!");
		}
		return (roomForPlanet == 1);
	}
	
	// prints all stars currently in database
	public void listStars() {
		for(int i = 0; i<Star.totalStars(); i++) {
			String name = stars[i].getName();
			double ra = stars[i].getRa();
			double dec = stars[i].getDec();
			String sType = stars[i].getSType();
			System.out.println("STAR " +name+ " " +ra+ " " +dec+ " " +sType);
		}
	}
	
	// takes the stars array index and prints all planets in orbit around the star
	public void listPlanets(int star) {
		for(int i = 0; i<stars[star].totalPlanets(); i++) {
			String starName = stars[star].getName();
			String name = stars[star].getPlanetName(i);
			double ra = stars[star].getPlanetRa(i);
			double dec = stars[star].getPlanetDec(i);
			System.out.println("PLANET " +name+ " " +ra+ " " +dec+ " " +starName);
		}
	}
	
	public void listObjectsWithinDistance(String origin, double range) {
		
		double angDist;
		
		// prints stars within specified range of object
		for(int i = 0; i<Star.totalStars(); i++) {
			if(!(stars[i].getName().equals(origin))) { // only tests whether object is within distance if NOT same object (obvious reasons)
				angDist = angularDistanceBetween(origin, stars[i].getName());
				if(angDist <= range)
					System.out.println("Star <" +stars[i].getName()+ "> has an angular distance <" +angDist+ "> from <" +origin+ ">");
			}
		}
		// prints planets in specified range of object
		for(int i = 0; i<Star.totalStars(); i++) {
			for(int j = 0; j<stars[i].totalPlanets(); j++) {
				if(!(stars[i].getPlanetName(j).equals(origin))) { // only tests whether object is within distance if NOT same object (obvious reasons)
					angDist = angularDistanceBetween(origin, stars[i].getPlanetName(j));
					if(angDist <= range)
						System.out.println("Planet <" +stars[i].getPlanetName(j)+ "> has an angular distance <" +angDist+ "> from <" +origin+ ">");
				}
			}
		}
		
	}
	
	// prints all planets in orbit around every star
	public void listAllPlanets() {
		for(int i = 0; i<Star.totalStars(); i++) {
			listPlanets(i);
		}
	}
	
	// prints all stars currently in database without max planets
	public void listStarsWithRoom() {
		for(int i = 0; i<Star.totalStars(); i++) {
			if(stars[i].starExists() && (stars[i].totalPlanets() != Star.getMaxPlanets())) {
				String name = stars[i].getName();
				double ra = stars[i].getRa();
				double dec = stars[i].getDec();
				String sType = stars[i].getSType();
				System.out.println("STAR " +name+ " " +ra+ " " +dec+ " " +sType);
			}
		}
	}
	
	// takes names of two objects in the database and returns the angular distance between them
	public double angularDistanceBetween(String objectA, String objectB) {
		double raA, raB;
		double decA, decB;
		int objectAInd, objectBInd;
		int objectAType, objectBType; // (-1) = star, any other number is the index of star which planet orbits
		
		objectAInd = inputStarNameMatches(objectA);
		objectAType = (-1); 
		if(objectAInd == (-1)) {
			for(int i = 0; i<Star.totalStars(); i++) {
				objectAInd = inputPlanetNameMatches(objectA, i);
				if(objectAInd != (-1)) {
					objectAType = i;
					break;
				}
			}
		}
		
		objectBInd = inputStarNameMatches(objectB);
		objectBType = (-1);
		if(objectBInd == (-1)) {
			for(int i = 0; i<Star.totalStars(); i++) {
				objectBInd = inputPlanetNameMatches(objectB, i);
				if(objectBInd != (-1)) {
					objectBType = i;
					break;
				}
			}
		}
		
		// if object type = (-1) then object was a star, and so ra and dec are pulled from star
		// otherwise object was a planet, and so ra and dec are pulled from planet orbiting star of index objectInd
		if(objectAType == (-1)) {
		raA = stars[objectAInd].getRa();
		decA = stars[objectAInd].getDec();
		} else {
			raA = stars[objectAType].getPlanetRa(objectAInd);
			decA = stars[objectAType].getPlanetDec(objectAInd);
		}
		
		if(objectBType == (-1)) {
		raB = stars[objectBInd].getRa();
		decB = stars[objectBInd].getDec();
		} else {
			raB = stars[objectBType].getPlanetRa(objectBInd);
			decB = stars[objectBType].getPlanetDec(objectBInd);
		}
		
		return getAngularDistance(raA, raB, decA, decB);
	}
}