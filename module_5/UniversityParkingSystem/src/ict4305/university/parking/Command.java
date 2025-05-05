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

public interface Command {
	String getCommandName(); // Gets the command's unique name
	String getDisplayName(); // Displays a name for the command
	String execute(Properties params); // completes/executes the commands logic
}
