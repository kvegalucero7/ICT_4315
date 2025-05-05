/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.observer;

import ict4305.university.parking.events.ParkingEvent;
import ict4305.university.parking.TransactionManager;

public class ParkingLotObserver implements ParkingObserver {
	private TransactionManager transactionManager;
	
	public ParkingLotObserver(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public void update(ParkingEvent event) { //This is needed to fix the class issue above
		if (event.isEntry()) {
			event.getParkingLot().increaseUsage(); // updating lot usage after entry
		} else {
			event.getParkingLot().decreaseUsage(); // updating lot usage after exit
		    transactionManager.park(event);
		}
	}
}
	
