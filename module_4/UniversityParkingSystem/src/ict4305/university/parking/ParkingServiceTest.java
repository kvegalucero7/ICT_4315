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
//import junit.runner.Version // used to check what version of JUnit is being used
//import java.util.Properties; // never used

/**
 * Tests for the ParkingService class.
 */
public class ParkingServiceTest {
	

    @Test
    public void testCommandRegistrationAndExecution() {
    	//System.out.println("JUnit Version is: " + Version.id()); // To check which version of JUnit I am using
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        ParkingService service = new ParkingService(office);

        RegisterCarCommand carCommand = new RegisterCarCommand(office);
        RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);

        // Register commands
        service.register(carCommand);
        service.register(customerCommand);

        // Execute valid customer registration
        String customerResult = service.performCommand("registerCustomer", new String[] {
            "name=John Doe",
            "streetAddress1=123 Main St",
            "streetAddress2=Apt 4B",
            "city=Denver",
            "state=CO",
            "zipCode=80202",
            "phoneNumber=5551234567"
        });
        
        //Validating customer was created successfully
        assertTrue(customerResult.contains("Customer registered successfully."));
        
        //Retrieving Random gen customer ID
        String customerId = service.extractCustomerId(customerResult);
        
        //Checking customer ID is exactly 12 digits
        assertEquals(6, customerId.length(), "Customer ID should be 6 digits.");
        assertTrue(customerId.matches("\\d{6}"), "Customer ID should only contain 6 digits.");
        
        // Execute valid car registration
        String carResult = service.performCommand("registerCar", new String[] {
            "customerId=" + customerId,
            "license=ABC123",
            "carType=COMPACT"
        });

        assertTrue(carResult.contains("Car registered successfully."));
    }

    @Test
    public void testCommandExecutionWithMissingParameters() {
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        ParkingService service = new ParkingService(office);

        RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
        service.register(customerCommand);

        // Missing parameters in command
        String customerResult = service.performCommand("registerCustomer", new String[] {
            "name=John Doe"
        });
        assertTrue(customerResult.contains("Missing required parameters"));
    }

    
    @Test
    public void testUnknownCommandExecution() {
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        ParkingService service = new ParkingService(office);

        // Execute an unregistered command
        String result = service.performCommand("unknownCommand", new String[] {});
        assertEquals("Command not found: unknownCommand", result);
    }

    @Test
    public void testEdgeCaseCommandRegistration() {
    	ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        ParkingService service = new ParkingService(office);

        RegisterCarCommand carCommand = new RegisterCarCommand(office);
        RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);

        // Register commands. The duplicate should overwrite the existing
        service.register(carCommand);
        service.register(carCommand);// Duplicate registration
        service.register(customerCommand);

        // Execute valid customer registration
        String customerResult = service.performCommand("registerCustomer", new String[] {
            "name=John Doe",
            "streetAddress1=123 Main St",
            "streetAddress2=Apt 4B",
            "city=Denver",
            "state=CO",
            "zipCode=80202",
            "phoneNumber=5551234567"
        });
        
        //Validating customer was created successfully
        assertTrue(customerResult.contains("Customer registered successfully."));
        
        //Retrieving Random gen customer ID
        String customerId = service.extractCustomerId(customerResult);
        
        // Execute valid car registration using extracted customerId
        String carResult = service.performCommand("registerCar", new String[] {
            "customerId=" + customerId,
            "license=ABC123",
            "carType=SUV"
        });

        // Validating registration result
        assertTrue(carResult.contains("Car registered successfully."));
    }
    
}
