// Name: Nicholas Worthington
// Student #: C3307819
//
//

public class Star {
//-------------------------------------------------------------------
//-------------------------------------------------------------------
// INSTANCE VARIABLES
	final private static int MAX_PLANETS = 2;
	private static int totalStars = 0;
	private int totalPlanets = 0;
	
	private String name, sType;
	private double ra, dec;
	private Planet[] planets = new Planet[MAX_PLANETS];

//-------------------------------------------------------------------
//-------------------------------------------------------------------
// METHODS
	// default star constructor
	public Star() {
		this.setName("");
		this.setRa(0);
		this.setDec(0);
		this.setSType("");
		for(int i = 0; i<MAX_PLANETS; i++) {
			planets[i] = new Planet();
		}
	}
	
	// star constructor taking name, right ascension, declination and spectral type as input
	public void setStar(String name, double ra, double dec, String sType) {
		totalStars += 1;
		this.setName(name);
		this.setRa(ra);
		this.setDec(dec);
		this.setSType(sType);
	}
	
	//copies data from star a into the star on which the method is called
	public void copyStar(Star star) {
		this.setName(star.getName());
		this.setRa(star.getRa());
		this.setDec(star.getDec());
		this.setSType(star.getSType());
	}
	
	public static void changeTotalStars(int delta) {
		totalStars += (delta);
	}
	
	// deletes star which method is called on and any planets in orbit
	public void deleteStar() {
		totalStars -= 1;
		this.setName("");
		this.setRa(0);
		this.setDec(0);
		this.setSType("");
		for(int planet = 0; planet<this.totalPlanets; planet++) {
			deletePlanet(planet);
		}
	}

	// takes an integer 1 or 2 and deletes the respective planet of the star
	public void deletePlanet(int planet) {
		planets[planet].deletePlanet();
		this.totalPlanets -= 1;
		for(int i = planet; i<(this.totalPlanets()); i++) {
			planets[i].copyPlanet(planets[i+1]);
			planets[i+1].deletePlanet();
		}
	}
	
	// prints information about a star
	public String getStarInfo() {
		return("Name: " +this.getName()
				+ "\nRight Ascension: " +this.getRa()
				+ "\nDeclination: " +this.getDec()
				+ "\nSpectral Type: " +this.getSType());
	}
	
	public static int totalStars() {
		return totalStars;
	}
	
	//Returns true if star exists
	public boolean starExists() {
		return (this.name != "");
	}
	
	// returns true only if input planet (1 or 2) exists in orbit around star
	public boolean planetExists(int n) {
		return (planets[n].getName() != "");
	}
	
	public boolean planetNameExists(String name) {
		for(int i = 0; i<totalPlanets; i++) {
			if(planets[i].getName().equals(name)) return true;
		}
		return false;
	}
	
	// returns integer number of planets in orbit of the star
	public int totalPlanets() {
		return totalPlanets;
	}
	
	//returns integer number of max planet limit per star
	static public int getMaxPlanets() {
		return MAX_PLANETS;
	}
	
	//returns true if star is at max planet limit, false if room for at least 1 planet
	public boolean atMaxPlanets() {
		return (totalPlanets == MAX_PLANETS);
	}
	
	// creates a new planet in orbit around star method is called on taking planet name, right ascension and declination as input
	public void createPlanet(String name, double ra, double dec) {
		for(int planet = 0; planet<Star.getMaxPlanets(); planet++) { //adds planet in first vacant array position
			if(getPlanetName(planet) == "") {
				planets[planet].setPlanet(name, ra, dec);
				totalPlanets += 1;
				break;
			}
		}
	}
	
	// returns index of planet if it exists in orbit around star, returns -1 if name not found
	public int inputPlanetNameMatches(String name, int planetIndex) {
		if(name.equals(planets[planetIndex].getName())) {
			return planetIndex;
		}
		return (-1);
	}
	
	// takes integer of planet as input and returns planet name
	public String getPlanetName(int n) {
		return planets[n].getName();
	}
	
	// takes integer of planet as input and returns planet right ascension
	public double getPlanetRa(int n) {
		return planets[n].getRa();
	}
	
	// takes integer of planet as input and returns planet declination
	public double getPlanetDec(int n) {
		return planets[n].getDec();
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
	
}
