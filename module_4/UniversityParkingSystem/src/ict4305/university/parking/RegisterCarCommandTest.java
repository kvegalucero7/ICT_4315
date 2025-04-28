/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 20, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Properties;

/**
 * Tests for the RegisterCarCommand class.
 */
public class RegisterCarCommandTest {

	@Test
	public void testCarRegistrationWithValidParameters() {
	    // Create a ParkingOffice with a valid address
	    ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
	    

	    // Register a customer in the ParkingOffice
	    office.registerCustomer("CUST001", "John Doe", new Address("123 Main St", null, "Denver", "CO", "80202"), "5551234567");
	    

	    // Create a RegisterCarCommand and associate it with the ParkingOffice
	    RegisterCarCommand command = new RegisterCarCommand(office);
	    

	    // Set up the parameters for the car registration command
	    Properties params = new Properties();
	    params.setProperty("customerId", "CUST001");
	    params.setProperty("license", "ABC123");
	    params.setProperty("carType", "COMPACT");

	    // Execute the command and assert the output
	    String result = command.execute(params);

	    // Validate that the result contains the expected success message and permit information
	    assertNotNull(result, "Result should not be null");
	    assertTrue(result.contains("Car registered successfully."), "Expected success message not found at the start of the result.");
	    assertTrue(result.contains("Permit"), "Expected permit information not found in result.");
	}

    @Test
    public void testCarRegistrationWithInvalidParameters() {
    	//Setting up the ParkingOffice
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        
        // Creating the RegisterCarCommand instance
        RegisterCarCommand command = new RegisterCarCommand(office);

        Properties params = new Properties();
        params.setProperty("customerId", "CUST001"); // Missing other parameters

        String result = command.execute(params);
        
        assertTrue(result.contains("Error: Missing required parameter:"));
    }

    //Testing with non existing customer
    @Test
    public void testCarRegistrationWithNonexistentCustomer() {
    	//Setting up the ParkingOffice
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        
        RegisterCarCommand command = new RegisterCarCommand(office);

        Properties params = new Properties();
        params.setProperty("customerId", "CUST999"); // Customer does not exist
        params.setProperty("license", "XYZ789");
        params.setProperty("carType", "SUV");

        String result = command.execute(params);
        assertTrue(result.contains("Customer not found for ID: CUST999"));
    }
    
    // Testing with a missing license parameter
    @Test
    public void testCarRegistrationWithMissingLicense() {
        // Set up ParkingOffice
        ParkingOffice office = new ParkingOffice("Test Office", new Address("123 Test St", null, "TestCity", "CO", "80202"));

        // Register a customer to associate the car with
        Customer customer = office.registerCustomer("CUST001", "John Doe", new Address("123 Test St", null, "TestCity", "CO", "80202"), "5551234567");

        // Create the RegisterCarCommand instance
        RegisterCarCommand command = new RegisterCarCommand(office);

        // Define input parameters without a license
        Properties params = new Properties();
        params.setProperty("customerId", "CUST001");
        params.setProperty("carType", "COMPACT");

        // Execute the command and capture the result
        String result = command.execute(params);

        // Validate the error message
        assertTrue(result.contains("Error"), "Expected error message for missing license not found.");
        assertTrue(result.contains("license"), "Error message should mention the missing 'license' parameter.");
    }

    
    
    @Test
    public void testCarRegistrationWithInvalidCarType() {
        // Set up ParkingOffice
        ParkingOffice office = new ParkingOffice("Test Office", new Address("123 Test St", null, "TestCity", "CO", "80202"));

        // Register a customer to associate the car with
        Customer customer = office.registerCustomer("CUST001", "John Doe", new Address("123 Test St", null, "TestCity", "CO", "80202"), "5551234567");

        // Create the RegisterCarCommand instance
        RegisterCarCommand command = new RegisterCarCommand(office);

        // Define input parameters with an invalid car type
        Properties params = new Properties();
        params.setProperty("customerId", "CUST001");
        params.setProperty("license", "XYZ123");
        params.setProperty("carType", "INVALID_TYPE");

        // Execute the command and capture the result
        String result = command.execute(params);

        // Validate the error message
        assertTrue(result.contains("Invalid car type"), "Expected error message for invalid car type not found.");
    }

    
    @Test
    public void testCarRegistrationWithMissingCustomerId() {
        // Set up ParkingOffice
        ParkingOffice office = new ParkingOffice("Test Office", new Address("123 Test St", null, "TestCity", "CO", "80202"));

        // Register a customer to associate the car with
        Customer customer = office.registerCustomer("CUST001", "John Doe", new Address("123 Test St", null, "TestCity", "CO", "80202"), "5551234567");

        // Creating the RegisterCarCommand instance
        RegisterCarCommand command = new RegisterCarCommand(office);

        // Define input parameters without a customer ID
        Properties params = new Properties();
        params.setProperty("license", "XYZ123");
        params.setProperty("carType", "COMPACT");

        // Execute the command and capture the result
        String result = command.execute(params);

        // Validate the error message
        assertTrue(result.contains("Error"), "Expected error message for missing customer ID not found.");
        assertTrue(result.contains("customerId"), "Error. Should mention the missing 'customerId' parameter.");
    }
}

