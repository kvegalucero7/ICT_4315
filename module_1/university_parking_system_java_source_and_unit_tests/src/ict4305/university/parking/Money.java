/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 1 Parking System (Continuation of ICT 4305)
 * April 6, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.math.BigDecimal;
import java.util.Objects;
import java.math.RoundingMode;

public class Money {
	private long cents;
	
	// Constructor for currency in smallest value available
	public Money(long cents) {
		if(cents < 0) {
			throw new IllegalArgumentException("Cents can't be negative.");
		}
		this.cents = cents;
	}
	
	// Getters
	// To retrieve cents - returns as a long 
	public long getCents() {
		return cents;
	}
	
	// To retrieve dollars - returns as a double
	public double getDollars() {
		return cents/100.00;
	}
	
	// To retrieve a sum of cents 
	public Money add(Money other) {
		return new Money(this.cents + other.cents);
	}
	
	// Equals method used to compare currency values
	// Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Money)) return false;
		Money money = (Money) o;
		return cents == money.cents;
	}
	
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() {
		return Objects.hash(cents);
	}
	
	// returns a string with a dollar value for the cents
	@Override
	public String toString() {
		BigDecimal dollars = BigDecimal.valueOf(getDollars()).setScale(2,RoundingMode.HALF_UP); // Work around for String.format using %.2f
		return "$" + dollars.toString();
	}
}

/*References:
 * 
 * GeeksforGeeks. 2024. "BigDecimal Class in Java." GeeksforGeeks.Updated March 8, 2024. https://www.geeksforgeeks.org/bigdecimal-class-java/.
 */
