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

public interface ParkingObserver {
	void update(ParkingEvent event);
}
