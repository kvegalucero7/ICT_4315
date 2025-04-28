/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 27, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.math.BigDecimal;
import java.util.Objects;
import java.math.RoundingMode;

public class Money {
	private long cents;
	private BigDecimal amount;
	
	// Constructor for currency in the smallest value available
	public Money(long cents) {
		if(cents < 0) {
			throw new IllegalArgumentException("Cents can't be negative.");
		}
		this.cents = cents;
		// Dividing cents by 100 to two decimals and rounding up
		this.amount = BigDecimal.valueOf(cents).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		//this.amount = BigDecimal.valueOf(cents).divide(BigDecimal.valueOf(100), 2); // this version was marking as deprecated

	}
	
	// Constructor for currency value as a BigDecimal
	public Money(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Cents can't be negative.");
		}
		this.cents = amount.multiply(BigDecimal.valueOf(100)).longValueExact();
		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
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
		Objects.requireNonNull(other, "Other Money object can't be null.");
		return new Money(this.cents + other.cents);
	}
	
	// To be able to Multiply a Money object by a multiplier
	public Money multiply(double multiplier) {
		if (multiplier < 0 ) {
			throw new IllegalArgumentException("Multiplier can't be negative");
		}
		// Return below sets BigDecimal scale to two decimal places rounding to nearest neighbor but half goes up
		//return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)).setScale(2, RoundingMode.HALF_UP));

		return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)).setScale(2));
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
