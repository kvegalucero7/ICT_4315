/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 1 Parking System (Continuation of ICT 4305)
 * April 6, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

/*
 * This class represent a customer that can register their cars and park in the university parking lots
 * It keeps record of the customer ID, name, address, phone number, and all of their registered cars 
 */

public class Customer {
	private String customerId; // Required unique customer ID for each customer
	private String name; // Required customer's name
	private Address address; // Required customer's address
	private String phoneNumber; // Required customer's phone number
	private List<Car> cars; // List of cars registered  to the customer
	private Set<String> permitIds; // List of all car permits to prevents to aid in preventing duplicate permits
	
	// To register and generate customer ID in ParkingOffice
	public Customer(String name, Address address, String phoneNumber) {
		// Preventing null or empty customerId
		if(customerId == null || customerId.isEmpty()) {
			throw new IllegalArgumentException("Customer ID can't be null or empty.");
		}
		// Preventing null or empty name
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Customer name can't be null or empty.");
		}
		// Preventing null address
		if(address == null) {
			throw new IllegalArgumentException("Customer address can't be null.");
		}
		// Preventing null or empty phone number
		if(phoneNumber == null || phoneNumber.isEmpty()) {
			throw new IllegalArgumentException("Customer phone number can't be null or empty.");
		}
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.cars = new ArrayList<>();
		this.permitIds = new HashSet<>();
	}
	
	// For customer IDs already generated
	public Customer(String customerId, String name, Address address, String phoneNumber) {
		// Preventing null or empty customerId
		if(customerId == null || customerId.isEmpty()) {
			throw new IllegalArgumentException("Customer ID can't be null or empty.");
		}
		// Preventing null or empty name
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Customer name can't be null or empty.");
		}
		// Preventing null address
		if(address == null) {
			throw new IllegalArgumentException("Customer address can't be null.");
		}
		// Preventing null or empty phone number
		if(phoneNumber == null || phoneNumber.isEmpty() || !isValidPhoneNumber(phoneNumber)) {
			throw new IllegalArgumentException("Customer phone number is invalid.");
		}
		this.customerId = customerId;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.cars = new ArrayList<>();
		this.permitIds = new HashSet<>();
	}
	
	private boolean isValidPhoneNumber(String phoneNumber) {
		return phoneNumber.matches("\\d{10}"); // checking for valid 10 digit number
	}
	
	// Getters
	// Returns customer ID
	public String getCustomerId() {
		return customerId;
	}
	
	// Returns customer name
	public String getName() {
		return name;
	}
	
	// Return customer address
	public Address getAddress() {
		return address;
	}
	
	// Return customer phone number
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	// Return list of customer cars
	public List<Car> getCars(){
		return cars;
	}
	
	
	//Generating a random permit number instead of chronological permit order
	public String generatePermit() {
		String permitId;
		do {
			char letter = (char)('A' + Math.random()*26); //Random letter A-Z
			int digits = (int) (Math.random()*100_000); //Random num 00000-99999
			permitId = letter + String.format("%05d", digits);
		}while(permitIds.contains(customerId));
		permitIds.add(permitId);
		return permitId;
	}
	
	
	/* 
	* Registers a new car for the customer
	* license makes note of the car's license plate
	* type makes note of the  car's type: COMPACT or SUV
	* returns the newly registered car
	*/
	public Car registerCar(String license, CarType type){
		if (license == null || license.isEmpty()) { //to catch null or empty licenses
			throw new IllegalArgumentException("License can't be null or empty.");
		}
		if (type == null) {  //to catch null car types
			throw new IllegalArgumentException("Car Type can't be null.");
		}
		//Removing line below to replace with random permitId
		//String permit = license; //permit will be the same as license to make gen random update here w/ method
		LocalDate permitExp = LocalDate.now().plusYears(1);
		Car car = new Car(generatePermit(), permitExp, license, type, this.customerId);
		cars.add(car);
		return car;
	}
	
	// Returning a string of customer all customer details
	@Override
	public String toString() {
		return "Customer--["+
	             "customerId=" + customerId + 
	             ", name=" + name + 
	             ", address=" + address.getAddressInfo() + 
				 ", phoneNumber=" + phoneNumber + 
				 ", cars=" + cars + 
			   "]"; 
	}
	
	// Equals method used to compare customers
    // Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass()!= o.getClass()) return false;
		Customer customer = (Customer) o;
		return customerId.equals(customer.customerId) &&
				name.equals(customer.name) &&
				address.equals(customer.address) &&
				phoneNumber.equals(customer.phoneNumber);
				
	}
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	 public int hashCode() {
		 return Objects.hash(customerId, name, address, phoneNumber);  
	 }
}
