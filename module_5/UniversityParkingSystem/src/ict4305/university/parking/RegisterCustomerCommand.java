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

import java.util.Properties;

public class RegisterCustomerCommand implements Command{
	private ParkingOffice office;
	
	// Makes sure command is tied to a valid ParkingOffice
	public RegisterCustomerCommand(ParkingOffice office){
		if(office == null ) {
			throw new IllegalArgumentException("ParkingOffice can't be null.");
		}
		this.office = office;
	}
	
	// Retrieves a name for the command
	@Override
	public String getCommandName() {
		return ("registerCustomer");
	}
	
	// retrieves the display name for the command
	@Override
	public String getDisplayName() {
		return("Register a New Customer.");
	}
	
	// Executes the logic behind the command with the parameters provides
	@Override 
	public String execute(Properties params) {
		try {
			// Validate input parameters
			checkParameters(params);
			
			//Extract parameters
			String customerId = params.getProperty("customerId"); //Optional
			String name = params.getProperty("name");
			String streetAddress1 = params.getProperty("streetAddress1");
			String streetAddress2 = params.getProperty("streetAddress2"); // Optional 
			String city = params.getProperty("city");
			String state = params.getProperty("state");
			String zipCode = params.getProperty("zipCode");
			String phoneNumber = params.getProperty("phoneNumber");
			
			// Validating required fields
			if (name == null || streetAddress1 == null || city == null ||
					state == null || zipCode == null || phoneNumber == null) {
				throw new IllegalArgumentException("Missing required parameters: customerId , name, "
						+ "streetAddress1, city, state, zipCode, phoneNumber");
			}
			
			//Creating an address object
			Address address = new Address(streetAddress1, streetAddress2, city, state, zipCode);
			
			//Registering the customer in the Parking office
			Customer customer; // work around to  registering the customer (Need to resolve)
			if (customerId != null && !customerId.isEmpty()) {
				customer = office.registerCustomer(customerId, address, phoneNumber);
			}else {
				customer = office.registerCustomer(name, address,phoneNumber);
			}
			
			return ("Customer registered successfully. Customer ID: " + customer.getCustomerId());
		}catch (IllegalArgumentException e) {
			return ("Error: " + e.getMessage());
		}
	}
	
	private void checkParameters(Properties params) {
		//checking for null parameters
        if (params == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
        
        //Checking for null and isEmpty for name, streetAddress1, city, state, zipCode, and phoneNu
        if (params.getProperty("name") == null || params.getProperty("name").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: name.");
        }
        if (params.getProperty("streetAddress1") == null || params.getProperty("streetAddress1").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: streetAddress1.");
        }
        if (params.getProperty("city") == null || params.getProperty("city").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: city.");
        }
        if (params.getProperty("state") == null || params.getProperty("state").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: state.");
        }
        if (params.getProperty("zipCode") == null || params.getProperty("zipCode").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: zipCode.");
        }
        if (params.getProperty("phoneNumber") == null || params.getProperty("phoneNumber").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: phoneNumber.");
        }
    }

}
