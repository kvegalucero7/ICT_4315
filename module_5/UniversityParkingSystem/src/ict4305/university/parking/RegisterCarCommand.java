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
import java.util.Optional;

public class RegisterCarCommand implements Command{
	private ParkingOffice office; //referencing instance of ParkingOffice
	
	// The constructor makes it so that the command is linked to a valid ParkingOffice
	public RegisterCarCommand(ParkingOffice office) {
		if(office == null) {
			throw new IllegalArgumentException("ParkingOffice can't be null.");
		}
		this.office = office;
	}
	
	// Retrieves the name of this command
	@Override
	public String getCommandName() {
		return("registerCar");
	}
	
	// Retrieves the display name for this command
	@Override
	public String getDisplayName() {
		return ("Register a New Car");
	}
	
	// executes the command of registering a car using the parameters provided
	@Override
	public String execute(Properties params) {
		try {
			// Validating input parameters
			checkParameters(params);
			
			// Extracting required parameters
			String customerId = params.getProperty("customerId");
			String license = params.getProperty("license");
			String carType = params.getProperty("carType");
			
			// notifying of missing car registration details
			if (customerId == null || customerId.isEmpty() || 
					license == null || license.isEmpty() || 
					carType == null || carType.isEmpty()) {
				throw new IllegalArgumentException("Missing required car registration details: customerId, license, carType.");
			}
		
			// Retrieve customer by their customerId as an optional from ParkingOffice
			Optional<Customer> optionalCustomer = office.getCustomerById(customerId);
			Customer customer = optionalCustomer.orElseThrow(() ->
			    new IllegalArgumentException("Customer not found for ID: " + customerId));
			
			//Parsing the carType
			CarType type;
			try{
				type = CarType.valueOf(carType.toUpperCase());
			}catch(IllegalArgumentException e){
				throw new IllegalArgumentException("Invalid car type: " + carType + ". Allowed types: COMPACT, SUV. ");
			}
			
			// Register the car using ParkingOffice
			Car car = office.registerCar(customer, license, type);
			
			//Returning successful car registration
			return ("Car registered successfully. Permit: " + car.getPermit());
			
		}catch(IllegalArgumentException e ) {
			return "Error: " + e.getMessage();
		}
	}
	
	private void checkParameters(Properties params) {
		//checking null parameters
		if (params == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
		//checking for null and empty parameters within customerId, license, and carType
        if (params.getProperty("customerId") == null || params.getProperty("customerId").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: customerId.");
        }
        if (params.getProperty("license") == null || params.getProperty("license").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: license.");
        }
        if (params.getProperty("carType") == null || params.getProperty("carType").isEmpty()) {
            throw new IllegalArgumentException("Missing required parameter: carType.");
        }

	}
}
