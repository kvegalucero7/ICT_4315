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

public class RegisterCustomerCommandTest {

    @Test
    public void testCustomerRegistrationWithValidParameters() {
        // Set up ParkingOffice
        ParkingOffice office = new ParkingOffice("Test Office", new Address("123 Test St", null, "TestCity", "CO", "80202"));

        // Create RegisterCustomerCommand
        RegisterCustomerCommand command = new RegisterCustomerCommand(office);

        // Set up valid parameters for customer registration
        Properties params = new Properties();
        params.setProperty("name", "John Doe");
        params.setProperty("streetAddress1", "123 Test St");
        params.setProperty("streetAddress2", "Apt 4B");
        params.setProperty("city", "TestCity");
        params.setProperty("state", "CO");
        params.setProperty("zipCode", "80202");
        params.setProperty("phoneNumber", "5551234567");

        // Execute the command
        String result = command.execute(params);

        // Validate the result using assertEquals
        assertEquals(true, result.startsWith("Customer registered successfully"), "The success message was not as expected.");
        assertTrue(result.contains("Customer ID"), "Expected Customer ID information not found in result.");
    }

    @Test
    public void testCustomerRegistrationWithMissingParameters() {
        // Set up ParkingOffice
        ParkingOffice office = new ParkingOffice("Test Office", new Address("123 Test St", null, "TestCity", "CO", "80202"));

        // Create RegisterCustomerCommand
        RegisterCustomerCommand command = new RegisterCustomerCommand(office);

        // Set up parameters with missing fields
        Properties params = new Properties();
        params.setProperty("name", "John Doe"); // Missing address and phone number

        // Execute the command
        String result = command.execute(params);

        // Validate the result using assertEquals
        assertTrue(result.contains("Error"), "Missing required parameters: customerId , name, streetAddress1, city, state, "
        		+ "zipCode, phoneNumber");
    }

    @Test
    public void testCustomerRegistrationWithInvalidZipCode() {
        // Set up ParkingOffice
        ParkingOffice office = new ParkingOffice("Test Office", new Address("123 Test St", null, "TestCity", "CO", "80202"));

        // Create RegisterCustomerCommand
        RegisterCustomerCommand command = new RegisterCustomerCommand(office);

        // Set up parameters with an invalid zip code
        Properties params = new Properties();
        params.setProperty("name", "John Doe");
        params.setProperty("streetAddress1", "123 Test St");
        params.setProperty("city", "TestCity");
        params.setProperty("state", "CO");
        params.setProperty("zipCode", "invalid_zip"); // Invalid zip code
        params.setProperty("phoneNumber", "5551234567");

        // Execute the command
        String result = command.execute(params);

        // Validate the result using assertEquals
        assertTrue(result.contains("Error"), "Zip code is invalid.");
    }

    @Test
    public void testCustomerRegistrationWithInvalidPhoneNumber() {
        // Set up ParkingOffice
        ParkingOffice office = new ParkingOffice("Test Office", new Address("123 Central St", null, "TestCity", "CO", "80202"));

        // Create RegisterCustomerCommand
        RegisterCustomerCommand command = new RegisterCustomerCommand(office);

        // Set up parameters with an invalid phone number
        Properties params = new Properties();
        params.setProperty("name", "John Doe");
        params.setProperty("streetAddress1", "123 Central St");
        params.setProperty("city", "Parker");
        params.setProperty("state", "CO");
        params.setProperty("zipCode", "80202");
        params.setProperty("phoneNumber", "invalid_phone"); // Invalid phone number

        // Execute the command
        String result = command.execute(params);
        
        // Validate the result using assertEquals
        assertTrue(result.contains("Error"), "Customer phone number is invalid.");
    }
}
