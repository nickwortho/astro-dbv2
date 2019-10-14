// Name: Nicholas Worthington
// Student #: C3307819
//
//

public class Planet {
	
	private String name;
	private double ra, dec;
	private Star star;
	
	// default planet constructor
	public Planet() {
		this.setName("");
		this.setRa(0);
		this.setDec(0);
	}
	
	// planet constructor taking name, right ascension and declination
	public void setPlanet(String name, double ra, double dec) {
		this.setName(name);
		this.setRa(ra);
		this.setDec(dec);
	}
	
	// deletes planet
	public void deletePlanet() {
		this.setName("");
		this.setRa(0);
		this.setDec(0);
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
	
	public Star getStar() {
		return star;
	}
	
	public void setStar(Star star) {
		this.star = star;
	}
	
}
