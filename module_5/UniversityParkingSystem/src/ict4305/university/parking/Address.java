/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;
import java.util.Objects;

/*
 * This class works with a postal address which includes the
 * street address, city, state, and zip code
 */

public class Address {
	private String streetAddress1; // Required field for primary street address
	private String streetAddress2; // Optional field for additional street address
	private String city; // Required address city
	private String state; // Required address state
	private String zipCode; // Required address zip code
	
	
	public Address(String streetAddress1, String streetAddress2, String city, String state, String zipCode) {
		// Preventing null or empty streetAddress1
		if (streetAddress1 == null || streetAddress1.isEmpty()) {
			throw new IllegalArgumentException("Street address 1 can't be null or empty.");
		}
		// Preventing null or empty city
		if (city == null || city.isEmpty()) {
			throw new IllegalArgumentException("City can't be null or empty.");
		}
		// Preventing null or empty state
		if (state == null || state.isEmpty()) {
			throw new IllegalArgumentException("State can't be null or empty.");
		}
		// Preventing an invalid zipcCode
		if (zipCode == null || zipCode.isEmpty() || !isValidZipCode(zipCode)) {
			throw new IllegalArgumentException("Zip code is invalid.");
		}
		this.streetAddress1 = streetAddress1;
		this.streetAddress2 = streetAddress2;
		this.city = city;
		this.state  =state;
		this.zipCode = zipCode;
	}
	
	// Ensuring the zip code is 5 digits
	private boolean isValidZipCode(String zipCode) {
		return zipCode.matches("\\d{5}");
	}
	
	
	// Getters
	// Gets all address components
	public String getAddressInfo() {
		return streetAddress1 + " " + streetAddress2 + ", " + city + ", " + state + " " + zipCode; 
	}
	
	
	// Returns the required street address
	public String getStreetAddress1() {
		return streetAddress1;
	}
	
	
	//Returns the optional street address details
	public String getStreetAddress2() {
		return streetAddress2;
	}
	
	
	// Returns the city
	public String getCity() {
		return city;
	}
	
	// Returns the state
	public String getState() {
		return state;
	}
	
	
	// Returns the zip code
	public String getZipCode() {
		return zipCode;
	}
	
	
	// Returns the full address as a string
	@Override
	public String toString() {
		return getAddressInfo();
	}
	
	// Equals method used to compare addresses
	// Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return streetAddress1.equals(address.streetAddress1) &&
				Objects.equals(streetAddress2, address.streetAddress2) &&
				city.equals(address.city) && 
				state.equals(address.state) &&
				zipCode.equals(address.zipCode);
	}
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() {
		return Objects.hash(streetAddress1, streetAddress2, city, state, zipCode);
	}
}
