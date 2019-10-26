// Name: Nicholas Worthington
// Student #: C3307819
//
//

public class Star {

	final private int MAX_PLANETS = 2;
	private static int numberOfStars = 0;
	
	private String name, sType;
	private double ra, dec;
	private Planet Planet[] = new Planet[MAX_PLANETS];
	
	// returns number of stars currently in the database
	public static int numberOfStars() {
		return numberOfStars;
	}
	
	// default star constructor
	public Star() {
		this.setName("");
		this.setRa(0);
		this.setDec(0);
		this.setSType("");
	}
	
	// star constructor taking name, right ascension, declination and spectral type as input
	public void setStar(String name, double ra, double dec, String sType) {
		numberOfStars += 1;
		this.setName(name);
		this.setRa(ra);
		this.setDec(dec);
		this.setSType(sType);
	}
	
	// deletes star which method is called on and any planets in orbit
	public void deleteStar() {
		numberOfStars -= 1;
		this.setName("");
		this.setRa(0);
		this.setDec(0);
		this.setSType("");
		planet1.deletePlanet();
		planet2.deletePlanet();
	}

	// takes an integer 1 or 2 and deletes the respective planet of the star
	public void deletePlanet(int planet) {
		if(planet == 1) planet1.deletePlanet();
		if(planet == 2) planet1.deletePlanet();
	}
	
	// prints information about a star
	public void getStarInfo() {
		System.out.println("Name: " +this.getName());
		System.out.println("Right Ascension: " +this.getRa());
		System.out.println("Declination: " +this.getDec());
		System.out.println("Spectral Type: " +this.getSType());
	}
	
	//Returns true if star exists
	public boolean starExists() {
		return (this.name != "");
	}
	
	// returns true only if input planet (1 or 2) exists in orbit around star
	public boolean planetExists(int n) {
		if(n == 1) {
			return (planet1.getName() != "");
		} else if(n == 2) {
			return (planet2.getName() != "");
		} else {
			return false;
		}
	}
	
	// returns integer number of planets in orbit of the star
	public int numberOfPlanets() {
		int numberOfPlanets = 0;
		if(planetExists(1)) numberOfPlanets += 1;
		if(planetExists(2)) numberOfPlanets += 1;
		return numberOfPlanets;
	}
	
	// adds a new planet in orbit around star taking which planet (1 or 2), name, right ascension and declination
	public void addPlanet(int n, String name, double ra, double dec) {
		if(n == 1) {
			planet1.setPlanet(name, ra, dec);
		} else {
			planet2.setPlanet(name, ra, dec);
		}
		
	}
	
	// takes integer of planet as input and returns planet name
	public String getPlanetName(int n) {
		if (n == 1) {
			return planet1.getName();
		} else if (n == 2) {
			return planet2.getName();
		} else {
			return "ERROR";
		}
	}
	
	// takes integer of planet as input and returns planet right ascension
	public double getPlanetRa(int n) {
		if (n == 1) {
			return planet1.getRa();
		} else if (n == 2) {
			return planet2.getRa();
		} else {
			return -1;
		}
	}
	
	// takes integer of planet as input and returns planet declination
	public double getPlanetDec(int n) {
		if (n == 1) {
			return planet1.getDec();
		} else if (n == 2) {
			return planet2.getDec();
		} else {
			return -1;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name.toLowerCase();
	}
	
	public double getRa() {
		return ra;
	}
	
	public void setRa(double ra) {
		this.ra = ra;
	}
	
	public double getDec() {
		return dec;
	}
	
	public void setDec(double dec) {
		this.dec = dec;
	}
	
	public String getSType() {
		return sType;
	}
	
	public void setSType(String sType) {
		this.sType = sType;
	}
	
	/*
	public static String checkForMaxStars() {
		if (Star.numberOfStars == 2)
			return ("There are already 2 stars in the database! Please delete one and try again.");
		return;
	}
	*/
	
}
