// Name: Nicholas Worthington
// Student #: C3307819
//
//
//

import java.util.Scanner;

public class Interface {
	
	final private static int MAX_STARS = 2;
	private Star[] stars = new Star[MAX_STARS];
	private int totalStars = 0;
	
	private void run(){
		
		Scanner console = new Scanner(System.in);
		
		int end = 1; // variable for user to quit program, will not loop menu if 0
		int skipQuit = 0; // if 1, skips default quit sequence
		
		do {
			
			String name, sType, orbitedStar; // variables holding user input for use in actions
			//double ra = 0, dec = 0;
			int loop; // variable used to determine running of various while loops
			double angDistance;
			
			showMenu();
			int action = console.nextInt(); // stores users choice of action
			console.nextLine();
			
			
			// OPERATIONS FOR CHOSEN ACTION
			switch(action) {
				case 1: // Add a star
					
					// returns an error if max stars already exist in database
					if (totalStars == MAX_STARS) {
						System.out.println("The database is full of stars! Please delete one and try again."); 
						break;
					}
					
					System.out.println("\nAdding a star...\n");
					System.out.println("When adding a star, note that right ascension ranges from 0 to 360,"
							+ " declination from -90 to 90,"
							+ "	and spectral type is a combination of a letter (OBAFGKM) followed by a number (0-9)");
					
					// prompts user for star's name
					inputStarName();
					
					// prompts user for star's right ascension
					inputStarRa();
					
					// prompts user for star's declination
					inputStarDec();
					
					// prompts user for star's spectral type
					inputStarSType();
					
					createStar();
					
					break;
					
				case 2: // Add a planet 
					
					// returns error if no stars in the database to add a planet to
					if (totalStars == 0) {
						System.out.println("You must have at least one star in the database before adding a planet.");
						break;
					}
					
					// returns error if there is no room for a planet in the database
					int roomForPlanet = 0;
					while(roomForPlanet == 0) {
						for(int i = 0; i<totalStars; i++) {
							if(stars[i].numberOfPlanets() != Star.getMaxPlanets()) {
								roomForPlanet = 1;
							}
						}
					}
					if(roomForPlanet == 0) {
						System.out.println("There is no room for another planet in the database!");
						break;
					}
				
					
					System.out.println("\nAdding a planet...\n");
					
					// Orbited star
					int orbitedStarInt; //integer form of the input star given it matches a star (1, 2) loops while 0
					do {
						orbitedStarInt = 0;
						
						// prompts user for star of which the planet orbits
						System.out.println("Which star does the planet orbit: ");
						//prints list of current stars
						for(int i = 0; i<totalStars; i++) {
							if(stars[i].starExists() && stars[i].numberOfPlanets() != 2) System.out.println(stars[i].getName());
						}
						
						orbitedStar = console.nextLine().toLowerCase(); // converts orbited star input to lower case
						
						//matches user input star to an integer value orbitedStarInt
						for(int i = 0; i<totalStars; i++) {
							if(orbitedStar.equals(stars[i].getName())) orbitedStarInt = (i + 1);
						}
						
						if(stars[orbitedStarInt - 1].numberOfPlanets() == 2) { // returns error if orbited star entered has 2 orbiting planets
							System.out.println("This star already has two orbitters!");
							orbitedStarInt = 0; //reset to 0 solely to continue loop
						}
		
					} while (orbitedStarInt == 0);
					

					// prompts user for planet's name
					do {
						loop = 0;
						System.out.println("Planet name: ");
						name = console.nextLine().toLowerCase(); // stores user input of planet name in lower case
						if (name.isEmpty()) {
							System.out.println("Planet name must not be blank!"); // error check for if user enters a blank name
							loop = 1;
							continue;
						}
						
						//checks for planets already in orbit of input star with input name
						for(int i = 0; i<Star.getMaxPlanets(); i++) { 
							if(name.equals(stars[orbitedStarInt - 1].getPlanetName(i))) {
								System.out.println("This planet already exists orbiting this star in the database! Please enter a different name");
								loop = 1;
							}
						}
						
					} while (loop == 1);
					
					
					// prompts user for planet's right ascension
					do { 
						System.out.println("Right Ascension: ");
						ra = console.nextDouble(); // stores user input of star right ascension
						console.nextLine();
						if (raOutsideRange(ra)) 
							System.out.println("Error. Value must be between 0 and 360"); // error for invalid right ascension
					} while (raOutsideRange(ra)); // prompts user again for right ascension if not within valid range
					
					// prompts user for planet's declination
					do {
						System.out.println("Declination:");
						dec = console.nextDouble(); // stores user input of star declination
						console.nextLine();
						if (decOutsideRange(dec)) 
							System.out.println("Error. Value must be between -90 and 90"); // error for invalid right ascension
					} while (decOutsideRange(dec)); // prompts user again for declination if not within valid range
					
					
					// adding the planet
					for(int i = 0; i<Star.getMaxPlanets(); i++) { //adds planet in first vacant array position
						if(stars[orbitedStarInt - 1].getPlanetName(i) != "") {
							stars[orbitedStarInt - 1].addPlanet(i, name, ra, dec);
							break;
						}
					}
					
					break;
					
					
				case 3: // Delete a star
					int nameInt = 0;
					
					if(totalStars == 0) { // error check if no stars exist in the database
						System.out.println("No stars currently in the database!");
						break;
					}
					
					System.out.println("\nDeleting a star...\n");
					System.out.println("Enter name of the star would you like to delete:");
					name = console.nextLine(); // assigns input for star name to name
					
					for(int i = 0; i<totalStars; i++) {
						if(name.toLowerCase().equals(stars[i].getName())) nameInt = (i + 1);
					}
					
					// deleting the star
					int starExists = 0;
					while (starExists == 0) {
						for(int i = 0; i<totalStars; i++) {
							if(name.toLowerCase().equals(stars[i].getName())) { //if input star is found in database, deletes it
								stars[i].deleteStar();
								System.out.println("Star "+name+" deleted!");
								starExists = 1;
								break;
							}
						}
					}
					if(starExists == 0) System.out.println("Star does not exist in database!"); //returns error if chosen star does not exist in the database
					
					break;
					
					
				case 4: // Delete a planet
					nameInt = 0; //stores planet selection in integer form (1-4) for star 1 and 2 respectively
					
					System.out.println("\nDeleting a planet...\n");
					
					if(totalStars == 0) { // returns error if no planets exist in the database
						System.out.println("No planets currently in the database!");
						break;
					}
					
					
					
					for(int i = 0; i<totalStars; i++) { // lists all existing planets
						if(stars[i].totalPlanets() != 0) {
							for(int j = 0; j<stars[i].totalPlanets(); j++) {
								System.out.println(stars[i].getPlanetName(j));
							}
						}
					}
					
					/*
					int existsAPlanet = 0;
					if(star1.numberOfPlanets() != 0) { // if star 1 has any planets in orbit
						System.out.println("Orbitting "+star1.getName()+":"); // prints the planets in orbit around star 1
						if(star1.getPlanetName(1) != "") {
							System.out.println(star1.getPlanetName(1));
						}
						if(star1.getPlanetName(2) != "") {
							System.out.println(star1.getPlanetName(2));
						}
						existsAPlanet = 1;
					}
					if(star2.numberOfPlanets() != 0) { // if star 2 has any planets in orbit
						System.out.println("Orbitting "+star2.getName()+":"); // prints the planets in orbit around star 1
						if(star2.getPlanetName(1) != "") {
							System.out.println(star2.getPlanetName(1));
						}
						if(star2.getPlanetName(2) != "") {
							System.out.println(star2.getPlanetName(2));
						}
						existsAPlanet = 1;
					}
					
					if(existsAPlanet == 0) { // returns error if no planets exist in the database
						System.out.println("No planets currently in the database");
						break;
					}
					*/
					
					System.out.println("Which planet would you like to delete?");
					
					do {
						name = console.nextLine().toLowerCase();
						
						// assigns chosen planet to a respective integer for easier use
						if(name.equals(star1.getPlanetName(1))) nameInt = 1;
						if(name.equals(star1.getPlanetName(2))) nameInt = 2;
						if(name.equals(star2.getPlanetName(1))) nameInt = 3;
						if(name.equals(star2.getPlanetName(2))) nameInt = 4;
						
						if(nameInt == 0) { // returns error and loops if name input was not an existing star name
							System.out.println("That planet does not exist in the database! Try again?");
							
						}
					} while(nameInt == 0);

					switch(nameInt) { // deletes selected planet
						case 1: 
							System.out.println("Planet "+star1.getPlanetName(1)+" has been deleted!");
							star1.deletePlanet(1);
							break;
						case 2: 
							System.out.println("Planet "+star1.getPlanetName(2)+" has been deleted!");
							star1.deletePlanet(2);
							break;
						case 3: 
							System.out.println("Planet "+star2.getPlanetName(1)+" has been deleted!");
							star2.deletePlanet(1);
							break;
						case 4: 
							System.out.println("Planet "+star2.getPlanetName(2)+" has been deleted!");
							star2.deletePlanet(2);
							break;
						default:
							break;
					}
					
					break;
					
				case 5: // Display list of all objects
					
					if(totalStars == 0) { // returns error if no objects exist in the database
						System.out.println("No astronomical objects exist in database!");
						break;
					}
					
					System.out.println("\nDisplaying all objects in database...\n");
					
					// if star 1 exists, prints its information and that of any planets in orbit
					if(star1.starExists()) {
						System.out.println("Star <"+star1.getName()+">: coordinate <"+star1.getRa()+"> <"
								+star1.getDec()+">, spectral type <"+star1.getSType()+">");
						for(int i = 1; i <=2; i++) { // loops through planets and if they exist, prints their information
							if(star1.planetExists(i)) System.out.println("Planet <"+star1.getPlanetName(i)+">: coordinate <"+star1.getPlanetRa(i)+"> <"
									+star1.getPlanetDec(i)+">");
						}
					}
					
					// if star 2 exists, prints its information and that of any planets in orbit
					if(star2.starExists()) {
						System.out.println("Star <"+star2.getName()+">: coordinate <"+star2.getRa()+"> <"
								+star2.getDec()+">, spectral type <"+star2.getSType()+">");
						for(int i = 1; i <=2; i++) { // loops through planets and if they exist, prints their information
							if(star2.planetExists(i)) System.out.println("Planet <"+star2.getPlanetName(i)+">: coordinate <"+star2.getPlanetRa(i)+"> <"
									+star2.getPlanetDec(i)+">");
						}
					}
					
					break;
					
				case 6: // Display list of planets orbiting a star

					if(totalStars == 0) { // error check for if there are no stars in database (therefore no planets)
						System.out.println("No planets currently in the database!");
						break;
					} else if(star2.numberOfPlanets() == 0 && star1.numberOfPlanets() == 0) {  // error check for if there are no planets in database
						System.out.println("No planets currently in the database!");
						break;
					}
					System.out.println("\nDisplaying planets orbitting a star...\n");
					
					
					do {
						loop = 0;
						
						System.out.println("Which star would you like to find the orbiting planets of?");
						//prints each star if they exist
						if(star1.starExists() && star1.numberOfPlanets() > 0) System.out.println(star1.getName()); 
						if(star2.starExists() && star1.numberOfPlanets() > 0) System.out.println(star2.getName());
						
						name = console.nextLine().toLowerCase(); //takes user input for star name and converts to lower case
					
						if(name == "") { // error check for if input is blank
							System.out.println("Star name must not be blank!");
							loop = 1;
						} else if(!(name.equals(star1.getName())) && !(name.equals(star2.getName()))) { // error check for if input is not a recognized star
							System.out.println("The star entered does not exist in the database");
							loop = 1;
						} else if(name.equals(star1.getName()) && star1.numberOfPlanets() == 0) { // error check if star 1 chosen & has no orbitting planets
							System.out.println("This star has no orbitting planets!");
							loop = 1;
						} else if(name.equals(star2.getName()) && star2.numberOfPlanets() == 0) { // error check if star 2 chosen & has no orbitting planets
							System.out.println("This star has no orbitting planets!");
							loop = 1;
						}
					} while (loop == 1);
					
					// lists planets of chosen star if they exist
					if(name.equals(star1.getName())) {
						System.out.println("Planets orbitting "+name+":");
						for(int i = 1; i <=2; i++) {
							if(star1.planetExists(i)) System.out.println("Planet <"+star1.getPlanetName(i)+">: coordinate <"+star1.getPlanetRa(i)+"> <"
									+star1.getPlanetDec(i)+">");
						}
					} else if(name.equals(star2.getName())) {
						System.out.println("Planets orbitting "+name+":");
						for(int i = 1; i <=2; i++) {
							if(star1.planetExists(i)) System.out.println("Planet <"+star2.getPlanetName(i)+">: coordinate <"+star2.getPlanetRa(i)+"> <"
									+star1.getPlanetDec(i)+">");
						}
					}
					
					break;
					
					
				case 7: // Find angular distance between 2 objects
					String objectA, objectB;
					double ra2 = 0, dec2 = 0; // right ascension and declination of second object
					
					System.out.println("\nFinding angular distance between 2 objects...\n");
					
					if(totalStars == 0) { // error check for if there are no stars in database (therefore no objects)
						System.out.println("No objects currently in the database!");
						break;
					} else if (totalStars == 1) { // error check for if there is only one object in database
						if (star1.numberOfPlanets() == 0 && star2.numberOfPlanets() == 0) { 
							System.out.println("There is only one object in the database!");
							break;
						}
					}
					
					do {
						nameInt = 0;
						loop = 0;
						System.out.println("Which two objects do you want to find the angular distance between?");
						System.out.println("Object 1:");
						
						objectA = console.nextLine().toLowerCase(); // takes user input for first object
						
						// assigns chosen object to a respective integer for easier use
						if(objectA.equals(star1.getName())) nameInt = 1;
						if(objectA.equals(star2.getName())) nameInt = 2;
						if(objectA.equals(star1.getPlanetName(1))) nameInt = 3;
						if(objectA.equals(star1.getPlanetName(2))) nameInt = 4;
						if(objectA.equals(star2.getPlanetName(1))) nameInt = 5;
						if(objectA.equals(star2.getPlanetName(2))) nameInt = 6;
						
						if (objectA == "") { // returns error if object input is blank
							System.out.println("Object name cannot be blank! Try again.");
							loop = 1;
						} else if(nameInt == 0) { // returns error if object does not exist in the database
							System.out.println("Object does not exist in the database! Try again.");
							loop = 1;
						}
					} while (loop == 1);
						
					// gets values for right ascension and declination of object A
					switch(nameInt) {
					case 1:
						ra = star1.getRa();
						dec = star1.getDec();
						break;
					case 2:
						ra = star2.getRa();
						dec = star2.getDec();
						break;
					case 3:
						ra = star1.getPlanetRa(1);
						dec = star1.getPlanetDec(1);
						break;
					case 4:
						ra = star1.getPlanetRa(2);
						dec = star1.getPlanetDec(2);
						break;
					case 5:
						ra = star2.getPlanetRa(1);
						dec = star2.getPlanetDec(1);
						break;
					case 6:
						ra = star2.getPlanetRa(2);
						dec = star2.getPlanetDec(2);
						break;
					}
					
					do {
						nameInt = 0;
						loop = 0;
						System.out.println("Object 2:");
						
						objectB = console.nextLine().toLowerCase(); // takes user input for first object
					
						// assigns chosen object to a respective integer for easier use
						if(objectB.equals(star1.getName())) nameInt = 1;
						if(objectB.equals(star2.getName())) nameInt = 2;
						if(objectB.equals(star1.getPlanetName(1))) nameInt = 3;
						if(objectB.equals(star1.getPlanetName(2))) nameInt = 4;
						if(objectB.equals(star2.getPlanetName(1))) nameInt = 5;
						if(objectB.equals(star2.getPlanetName(2))) nameInt = 6;
						
						if (objectB == "") { // returns error is object input is blank
							System.out.println("Object name cannot be blank! Try again.");
							loop = 1;
						} else if(nameInt == 0) { // returns error if object does not exist in database
							System.out.println("Object does not exist in the database! Try again.");
							loop = 1;
						} else if(objectA.equals(objectB)) { // returns error if object input is the same as the first object
							System.out.println("Cannot find distance to the same object! Try again.");
						}
					} while (loop == 1);
					
					// gets values for right ascension and declination of object B
					switch(nameInt) {
						case 1:
							ra2 = star1.getRa();
							dec2 = star1.getDec();
							break;
						case 2:
							ra2 = star2.getRa();
							dec2 = star2.getDec();
							break;
						case 3:
							ra2 = star1.getPlanetDec(1);
							dec2 = star1.getPlanetDec(1);
							break;
						case 4:
							ra2 = star1.getPlanetDec(2);
							dec2 = star1.getPlanetDec(2);
							break;
						case 5:
							ra2 = star2.getPlanetDec(1);
							dec2 = star2.getPlanetDec(1);
							break;
						case 6:
							ra2 = star2.getPlanetDec(2);
							dec2 = star2.getPlanetDec(2);
							break;
					}
					
					angDistance = getAngularDistance(ra, ra2, dec, dec2);
					
					System.out.println("For "+objectA+" and "+objectB+":");
					System.out.println("Angular distance: <"+angDistance+">");
						
					break;
					
				case 8: // Find objects within a set angular distance of a certain object WORKING
					String object;
					angDistance = 0;
					
					System.out.println("\nFinding objects within a set angular distance of an object...\n");
					
					if(totalStars == 0) { // returns error if there are no stars in database (therefore no objects)
						System.out.println("No objects currently in the database!");
						break;
					} else if (totalStars == 1) { // returns error if there is only one object in database
						if (star1.numberOfPlanets() == 0 && star2.numberOfPlanets() == 0) {
							System.out.println("There is only one object currently in the database!");
							break;
						}
					}
					
					do {
						nameInt = 0;
						loop = 0;
						
						System.out.println("Which object would you like to set as the origin?");
						object = console.nextLine().toLowerCase(); // takes user input for origin object
						
						// assigns chosen object to a respective integer for easier use
						if(object.equals(star1.getName())) nameInt = 1;
						if(object.equals(star2.getName())) nameInt = 2;
						if(object.equals(star1.getPlanetName(1))) nameInt = 3;
						if(object.equals(star1.getPlanetName(2))) nameInt = 4;
						if(object.equals(star2.getPlanetName(1))) nameInt = 5;
						if(object.equals(star2.getPlanetName(2))) nameInt = 6;
						
						if (object == "") { // returns error if object name input is blank
							System.out.println("Object name cannot be blank! Try again.");
							loop = 1;
						} else if(nameInt == 0) { // returns error if input object does not exist in the database
							System.out.println("Object does not exist in the database! Try again.");
							loop = 1;
						}
					} while (loop == 1);
					
					do {
						loop = 0;
						System.out.println("Which angular distance would you like to search between?");
						angDistance = console.nextDouble(); // takes user input for angular distance range
						console.nextLine();
						
						if (angDistance < 0 || angDistance > 180) { // returns error if input angular distance is outside valid range (0-180 deg)
							System.out.println("Angular distance must be between 0 and 180 degrees! Try again.");
							loop = 1;
							break;
						}
					} while (loop == 1);
					
					
					int objectWithinRange = 0;
					switch(nameInt) {
						case 1: //checks all objects for within angDistance if star1 was input
							if(star2.starExists()) { // only runs if second object to compare exists
								if(getAngularDistance(star1.getRa(), star2.getRa(), star1.getDec(), star2.getDec()) <= angDistance) { // if star2 is within angDistance of object
									System.out.println("Star <"+star2.getName()+"> has angular distance <"+getAngularDistance(star1.getRa(), star2.getRa(), star1.getDec(), star2.getDec())+"> from "+object);
									objectWithinRange = 1; // records if there exists at least 1 other object within range of the input object
								}
							}
							if(star1.planetExists(1)) {
								if(getAngularDistance(star1.getRa(), star1.getPlanetRa(1), star1.getDec(), star1.getPlanetDec(1)) <= angDistance) { // if planet 1 of star 1 is within angDistance of object...etc
									System.out.println("Planet <"+star1.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star1.getRa(), star1.getPlanetRa(1), star1.getDec(), star1.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(2)) {
								if(getAngularDistance(star1.getRa(), star1.getPlanetRa(2), star1.getDec(), star1.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star1.getRa(), star1.getPlanetRa(2), star1.getDec(), star1.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(1)) {
								if(getAngularDistance(star1.getRa(), star2.getPlanetRa(1), star1.getDec(), star2.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star1.getRa(), star2.getPlanetRa(1), star1.getDec(), star2.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(2)) {
								if(getAngularDistance(star1.getRa(), star2.getPlanetRa(2), star1.getDec(), star2.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star1.getRa(), star2.getPlanetRa(2), star1.getDec(), star2.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							break;
							
						case 2: //checks all objects for within angDistance if star2 was input
							if(star1.starExists()) {
								if(getAngularDistance(star2.getRa(), star1.getRa(), star2.getDec(), star1.getDec()) <= angDistance) {
									System.out.println("Star <"+star1.getName()+"> has angular distance <"+getAngularDistance(star2.getRa(), star1.getRa(), star2.getDec(), star1.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(1)) {
								if(getAngularDistance(star2.getRa(), star1.getPlanetRa(1), star2.getDec(), star1.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star2.getRa(), star1.getPlanetRa(1), star2.getDec(), star1.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(2)) {
								if(getAngularDistance(star2.getRa(), star1.getPlanetRa(2), star2.getDec(), star1.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star2.getRa(), star1.getPlanetRa(2), star2.getDec(), star1.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(1)) {
								if(getAngularDistance(star2.getRa(), star2.getPlanetRa(1), star2.getDec(), star2.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star2.getRa(), star2.getPlanetRa(1), star2.getDec(), star2.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(2)) {
								if(getAngularDistance(star2.getRa(), star2.getPlanetRa(2), star2.getDec(), star2.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star2.getRa(), star2.getPlanetRa(2), star2.getDec(), star2.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							break;
							
						case 3: //checks all objects for within angDistance if planet 1 of star 1 was input
							if(star1.starExists()) {
								if(getAngularDistance(star1.getPlanetRa(1), star1.getRa(), star1.getPlanetDec(1), star1.getDec()) <= angDistance) {
									System.out.println("Star <"+star1.getName()+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(1), star1.getRa(), star1.getPlanetDec(1), star1.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.starExists()) {
								if(getAngularDistance(star1.getPlanetRa(1), star2.getRa(), star1.getPlanetDec(1), star2.getDec()) <= angDistance) {
									System.out.println("Star <"+star2.getName()+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(1), star2.getRa(), star1.getPlanetDec(1), star2.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(2)) {
								if(getAngularDistance(star1.getPlanetRa(1), star1.getPlanetRa(2), star1.getPlanetDec(1), star1.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(1), star1.getPlanetRa(2), star1.getPlanetDec(1), star1.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(1)) {
								if(getAngularDistance(star1.getPlanetRa(1), star2.getPlanetRa(1), star1.getPlanetDec(1), star2.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(1), star2.getPlanetRa(1), star1.getPlanetDec(1), star2.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(2)) {
								if(getAngularDistance(star1.getPlanetRa(1), star2.getPlanetRa(2), star1.getPlanetDec(1), star2.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(1), star2.getPlanetRa(2), star1.getPlanetDec(1), star2.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							break;
							
						case 4: //checks all objects for within angDistance if planet 2 of star 1 was input
							if(star1.starExists()) {
								if(getAngularDistance(star1.getPlanetRa(2), star1.getRa(), star1.getPlanetDec(2), star1.getDec()) <= angDistance) {
									System.out.println("Star <"+star1.getName()+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(2), star1.getRa(), star1.getPlanetDec(2), star1.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.starExists()) {
								if(getAngularDistance(star1.getPlanetRa(2), star2.getRa(), star1.getPlanetDec(2), star2.getDec()) <= angDistance) {
									System.out.println("Star <"+star2.getName()+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(2), star2.getRa(), star1.getPlanetDec(2), star2.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(1)) {
								if(getAngularDistance(star1.getPlanetRa(2), star1.getPlanetRa(1), star1.getPlanetDec(2), star1.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(2), star1.getPlanetRa(1), star1.getPlanetDec(2), star1.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(1)) {
								if(getAngularDistance(star1.getPlanetRa(2), star2.getPlanetRa(1), star1.getPlanetDec(2), star2.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(2), star2.getPlanetRa(1), star1.getPlanetDec(2), star2.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(2)) {
								if(getAngularDistance(star1.getPlanetRa(2), star2.getPlanetRa(2), star1.getPlanetDec(2), star2.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star1.getPlanetRa(2), star2.getPlanetRa(2), star1.getPlanetDec(2), star2.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							break;
							
						case 5: //checks all objects for within angDistance if planet 1 of star 2 was input
							if(star1.starExists()) {
								if(getAngularDistance(star2.getPlanetRa(1), star1.getRa(), star2.getPlanetDec(1), star1.getDec()) <= angDistance) {
									System.out.println("Star <"+star1.getName()+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(1), star1.getRa(), star2.getPlanetDec(1), star1.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.starExists()) {
								if(getAngularDistance(star2.getPlanetRa(1), star2.getRa(), star2.getPlanetDec(1), star2.getDec()) <= angDistance) {
									System.out.println("Star <"+star2.getName()+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(1), star2.getRa(), star2.getPlanetDec(1), star2.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(1)) {
								if(getAngularDistance(star2.getPlanetRa(1), star1.getPlanetRa(1), star2.getPlanetDec(1), star1.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(1), star1.getPlanetRa(1), star2.getPlanetDec(1), star1.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(2)) {
								if(getAngularDistance(star2.getPlanetRa(1), star1.getPlanetRa(2), star2.getPlanetDec(1), star1.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(1), star1.getPlanetRa(2), star2.getPlanetDec(1), star1.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(2)) {
								if(getAngularDistance(star2.getPlanetRa(1), star2.getPlanetRa(2), star2.getPlanetDec(1), star2.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(1), star2.getPlanetRa(2), star2.getPlanetDec(1), star2.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							break;
							
						case 6: //checks all objects for within angDistance if planet 2 of star 2 was input
							if(star1.starExists()) {
								if(getAngularDistance(star2.getPlanetRa(2), star1.getRa(), star2.getPlanetDec(2), star1.getDec()) <= angDistance) {
									System.out.println("Star <"+star1.getName()+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(2), star1.getRa(), star2.getPlanetDec(2), star1.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.starExists()) {
								if(getAngularDistance(star2.getPlanetRa(2), star2.getRa(), star2.getPlanetDec(2), star2.getDec()) <= angDistance) {
									System.out.println("Star <"+star2.getName()+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(2), star2.getRa(), star2.getPlanetDec(2), star2.getDec())+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(1)) {
								if(getAngularDistance(star2.getPlanetRa(2), star1.getPlanetRa(1), star2.getPlanetDec(2), star1.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(2), star1.getPlanetRa(1), star2.getPlanetDec(2), star1.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star1.planetExists(2)) {
								if(getAngularDistance(star2.getPlanetRa(2), star1.getPlanetRa(2), star2.getPlanetDec(2), star1.getPlanetDec(2)) <= angDistance) {
									System.out.println("Planet <"+star1.getPlanetName(2)+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(2), star1.getPlanetRa(2), star2.getPlanetDec(2), star1.getPlanetDec(2))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							if(star2.planetExists(1)) {
								if(getAngularDistance(star2.getPlanetRa(2), star2.getPlanetRa(1), star2.getPlanetDec(2), star2.getPlanetDec(1)) <= angDistance) {
									System.out.println("Planet <"+star2.getPlanetName(1)+"> has angular distance <"+getAngularDistance(star2.getPlanetRa(2), star2.getPlanetRa(1), star2.getPlanetDec(2), star2.getPlanetDec(1))+"> from "+object);
									objectWithinRange = 1;
								}
							}
							break;
					}
					
					if(objectWithinRange == 0) System.out.println("No other objects within angular distance "+angDistance+" from "+object);
					
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
	public static boolean raOutsideRange(double ra) {
		return (ra < 0 || ra > 360);
	}
		
	// Checks whether a given declination (dec) is within the valid range -90 <= dec <= 90
	// Returns true if valid, false otherwise.
	public static boolean decOutsideRange(double dec) {
		return (dec < (-90) || dec > (90));
	}
	
	// Gets the angular distance between two objects in the database
	// Takes right ascension and declination of both objects in degrees as input
	// Returns angular distance in degree
	public static double getAngularDistance(double ra, double ra2, double dec, double dec2) {
		return Math.toDegrees(Math.acos(Math.cos(Math.toRadians(ra) - Math.toRadians(ra2))*Math.cos(Math.toRadians(dec))*Math.cos(Math.toRadians(dec2))+(Math.sin(Math.toRadians(dec))*Math.sin(Math.toRadians(dec2)))));
	}
	
	public String inputStarName() {
		int loop;
		String name = "";
		Scanner console = new Scanner(System.in);
		do {
			System.out.println("Star name: ");
			name = console.nextLine(); // stores user input of star name
			if (name.isEmpty()) {
				System.out.println("Star name must not be blank!"); // error check, if user enters a blank name
				continue;
			}
			for(int i = 0; i<totalStars; i++) {
				if(name.equalsIgnoreCase(stars[i].getName())) {
					System.out.println("This star already exists in the database! Please enter a different name"); // if there exists a star name, checks if it is the same as user input	
				}
			}
		} while (name == "");
		console.close();
		return name;
	}
	
	public double inputStarRa() {
		double ra;
		Scanner console = new Scanner(System.in);
		do { 
			System.out.println("Right Ascension: ");
			ra = console.nextDouble(); // stores user input of star right ascension
			console.nextLine();
			if (raOutsideRange(ra)) 
				System.out.println("Error. Value must be between 0 and 360"); // error for invalid right ascension
		} while (raOutsideRange(ra)); // prompts user again for right ascension if not within valid range
		console.close();
		return ra;
	}
	
	public double inputStarDec() {
		double dec;
		Scanner console = new Scanner(System.in);
		do {
			System.out.println("Declination:");
			dec = console.nextDouble(); // stores user input of star declination
			console.nextLine();
			if (decOutsideRange(dec)) 
				System.out.println("Error. Value must be between -90 and 90"); // error for invalid right ascension
		} while (decOutsideRange(dec)); // prompts user again for declination if not within valid range
		console.close();
		return dec;
	}
	
	public String inputStarSType() {
		String sType;
		Scanner console = new Scanner(System.in);
		do {
			System.out.println("Spectral Type:");
			sType = console.nextLine(); // stores user input of star spectral type
			sType = sType.toUpperCase();
			if (!(sType.matches("[OBAFGKM][0-9]"))) 
				System.out.println("Error. Spectral type must be a letter (OBAFGKM) followed by a whole number (0-9)"); // error for invalid spectral type
		} while (!(sType.matches("[OBAFGKM][0-9]")));// prompts user again for spectral type if not valid
		console.close();
		return sType;
	}
	
	public void createStar(String name, double ra, double dec, String sType) {
		Scanner console = new Scanner(System.in);
		for(int i = 0; i<totalStars; i++) { 
			if(!(stars[i].starExists())) { //creates a star in first empty array element
				stars[i].setStar(name, ra, dec, sType); //instantiates star
				System.out.println("/nStar added!");
				System.out.println(stars[i].getStarInfo()); //displays added star back to user
			}
		}
	}
	
	
}