package nz.co.scuff.data.school;

import java.util.HashSet;

import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.family.Driver;

/**
 * Created by Callum on 17/03/2015.
 */
public class Bus {

    private String name;
    private Driver driver;
    private HashSet<Passenger> passengers;

    public Bus(String name) {
        this.name = name;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public boolean addPassenger(Passenger passenger) {
        return this.passengers.add(passenger);
    }

    public boolean removePassenger(Passenger passenger) {
        return this.passengers.remove(passenger);
    }

    public HashSet<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(HashSet<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}