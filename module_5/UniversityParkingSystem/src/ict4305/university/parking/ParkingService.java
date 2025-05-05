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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
//import java.util.Collections;

public class ParkingService {
	//private ParkingOffice office; //Associated ParkingOffice instance
	private Map<String, Command> commands; // Map of registered commands
	
	//Begins service with given ParkingOffice and creates an empty command log
	public ParkingService(ParkingOffice office) {
		if(office == null) {
			throw new IllegalArgumentException("ParkingOffice can't be null.");
		}
		//this.office = office;
		this.commands = new HashMap<>(); //Initializing the command storage
	}
	
	
	// Registers command with service for Parking Office to recognize and execute command
	public void register(Command command) {
		if(command == null || command.getCommandName() == null || command.getCommandName().isEmpty()) {
			throw new IllegalArgumentException("Invalid command.");
		}
		commands.put(command.getCommandName(),command);
	}
	
	//Searches for command. If found executes command with given parameter
	public String performCommand(String commandName, String[] parameters) {
		if (commandName == null) {
			throw new IllegalArgumentException("Command Name can't be null.");
		}
		//retrieving command
		Command command = commands.get(commandName);
		if(command == null || commandName.isEmpty()){
			return ("Unknown command: " + commandName);
		}
		
		// Turns an array into a Properties object to process easier
		Properties props = new Properties();
		for(String param : parameters) {
			String[] keyValue =  param.split("=", 2);
			if(keyValue.length ==2) {
				props.setProperty(keyValue[0], keyValue[1]);
			}
		}
		//executes command and returns result
		return command.execute(props);
	}
	
	//Extracts customerId from the result for easier processing since the customerId is randomly generated
	public String extractCustomerId(String result) {
		if (result == null || result.isEmpty()) {
			throw new IllegalArgumentException("Result can't be null or empty.");
		}
		
		String[] parts = result.split(":");
		return parts[1].trim();
	}
}
