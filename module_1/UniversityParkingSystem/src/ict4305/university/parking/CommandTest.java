/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 1 Parking System (Continuation of ICT 4305)
 * April 6, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Properties;


public class CommandTest {

    @Test
    public void testCommandNames() {
    	// Creating a ParkingOffice to use in commands
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        
        //Creating command instances
        RegisterCarCommand registerCarCommand = new RegisterCarCommand(office);
        RegisterCustomerCommand registerCustomerCommand = new RegisterCustomerCommand(office);

        // Test command names are correct
        assertEquals("registerCar", registerCarCommand.getCommandName());
        assertEquals("registerCustomer", registerCustomerCommand.getCommandName());
    }

    
    @Test
    public void testDisplayNames() {
    	//Creating ParkingOffice to use for commands
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        
        //Creating command instances
        RegisterCarCommand registerCarCommand = new RegisterCarCommand(office);
        RegisterCustomerCommand registerCustomerCommand = new RegisterCustomerCommand(office);

        // Test display names to ensure correct name displays
        assertEquals("Register a New Car", registerCarCommand.getDisplayName());
        assertEquals("Register a New Customer.", registerCustomerCommand.getDisplayName());
    }

    
    @Test
    public void testCommandExecutionWithMockProperties() {
    	// Create parking office for command execution using ParkingService
        ParkingOffice office = new ParkingOffice("Office1", new Address("123 Main St", null, "Denver", "CO", "80202"));
        ParkingService service = new ParkingService(office);

        //Registering commands properly 
        RegisterCustomerCommand registerCustomerCommand = new RegisterCustomerCommand(office);
        RegisterCarCommand registerCarCommand =new RegisterCarCommand(office);
        service.register(registerCustomerCommand);
        service.register(registerCarCommand);
        
     // Mock command execution for a simulation of user-provided properties when registering new customer
        Properties custProps = new Properties();
        custProps.setProperty("customerId", "CUST123");
        custProps.setProperty("name", "Sarah Smith");
        custProps.setProperty("streetAddress1", "1234 Main Road");
        custProps.setProperty("streetAddress2", "");
        custProps.setProperty("city", "Denver");
        custProps.setProperty("state", "Co");
        custProps.setProperty("zipCode", "80204");
        custProps.setProperty("phoneNumber", "5559876543");

        // Capturing result of executing RegisterCustomerCommand with mock properties
        String customerResult = registerCustomerCommand.execute(custProps);
        
        assertTrue(customerResult.startsWith("Customer registered successfully"), "Expected successful customer registration message not found.");
        assertTrue(customerResult.contains("Customer ID"), "Customer ID not found in customer registration");
        //assertTrue(customerResult.contains("Customer registered successfully"));
        
        //Extracting customerId to use in other actions
        String customerId = service.extractCustomerId(customerResult);
        
        // Mock command execution for a simulation of user-provided properties for registering a new car
        Properties carProps = new Properties();
        carProps.setProperty("customerId", customerId);
        carProps.setProperty("license", "TEST321");
        carProps.setProperty("carType", "COMPACT");
        
        // Capturing result of executing RegisterCarCommand with mock properties
        String carResult = registerCarCommand.execute(carProps);
        System.out.println(carResult);

        
        // Validating successful car registration and permit details are included
        assertTrue(carResult.startsWith("Car registered successfully"), "Expected successful car registration message not found.");
        assertTrue(carResult.contains("Permit"), "Expected permit details not found.");
    }
    
    
    /*
     * Testing with Invalid property entries
     */
    // Invalid customer ID
    @Test 
    public void testExecuteInvalidCustomerIdProperty() {
    	ParkingOffice office = new ParkingOffice("ParkingOffice1", new Address("2020 University Blvd", null, "Denver", "CO", "80202"));
    	
    	RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
    	Properties invalidProperty = new Properties();
    	invalidProperty.setProperty("customerId", "");// Invalid ID
    	
    	String result = customerCommand.execute(invalidProperty);
    	
    	assertTrue(result.contains("Error: Missing required parameter:"));
    }
    
    // Invalid name
    @Test 
    public void testExecuteInvalidNameProperty() {
    	ParkingOffice office = new ParkingOffice("ParkingOffice1", new Address("2020 University Blvd", null, "Denver", "CO", "80202"));
    	
    	RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
    	Properties invalidProperty = new Properties();
    	invalidProperty.setProperty("name", "");// Invalid name
    	
    	String result = customerCommand.execute(invalidProperty);
    	assertTrue(result.contains("Error: Missing required parameter:"));
    }
    
    // Invalid streetAddress1
    @Test 
    public void testExecuteInvalidStreetAddress1Property() {
    	ParkingOffice office = new ParkingOffice("ParkingOffice1", new Address("2020 University Blvd", null, "Denver", "CO", "80202"));
    	
    	RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
    	Properties invalidProperty = new Properties();
    	invalidProperty.setProperty("streetAddress1", "");// Invalid streetAddres1
    	
    	String result = customerCommand.execute(invalidProperty);
    	assertTrue(result.contains("Error: Missing required parameter:"));
    }
    
    // Invalid city
    @Test 
    public void testExecuteInvalidCityProperty() {
    	ParkingOffice office = new ParkingOffice("ParkingOffice1", new Address("2020 University Blvd", null, "Denver", "CO", "80202"));
    	
    	RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
    	Properties invalidProperty = new Properties();
    	invalidProperty.setProperty("city", "");// Invalid city
    	
    	String result = customerCommand.execute(invalidProperty);
    	assertTrue(result.contains("Error: Missing required parameter:"));
    }
    
    // Invalid state
    @Test 
    public void testExecuteInvalidStateProperty() {
    	ParkingOffice office = new ParkingOffice("ParkingOffice1", new Address("2020 University Blvd", null, "Denver", "CO", "80202"));
    	
    	RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
    	Properties invalidProperty = new Properties();
    	invalidProperty.setProperty("state", "");// Invalid state
    	
    	String result = customerCommand.execute(invalidProperty);
    	assertTrue(result.contains("Error: Missing required parameter:"));
    }
    
    // Invalid zipCode
    @Test 
    public void testExecuteInvalidZipCodeProperty() {
    	ParkingOffice office = new ParkingOffice("ParkingOffice1", new Address("2020 University Blvd", null, "Denver", "CO", "80202"));
    	
    	RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
    	Properties invalidProperty = new Properties();
    	invalidProperty.setProperty("zipCode", "");// Invalid zipCode
    	
    	String result = customerCommand.execute(invalidProperty);
    	assertTrue(result.contains("Error: Missing required parameter:"));
    }
    
    // Invalid PhoneNumber
    @Test 
    public void testExecuteInvalidPhoneNumberProperty() {
    	ParkingOffice office = new ParkingOffice("ParkingOffice1", new Address("2020 University Blvd", null, "Denver", "CO", "80202"));
    	
    	RegisterCustomerCommand customerCommand = new RegisterCustomerCommand(office);
    	Properties invalidProperty = new Properties();
    	invalidProperty.setProperty("phoneNumber", "");// Invalid phoneNumber
    	
    	String result = customerCommand.execute(invalidProperty);
    	assertTrue(result.contains("Error: Missing required parameter:"));
    }
}

