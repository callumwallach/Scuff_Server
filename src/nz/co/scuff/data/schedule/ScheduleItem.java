package nz.co.scuff.data.schedule;

import org.joda.time.DateTime;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.school.Route;

/**
 * Created by Callum on 17/03/2015.
 */
public class ScheduleItem {

    private Driver driver;
    private Route route;
    private DateTime date;

    public ScheduleItem(Driver driver, Route route, DateTime date) {
        this.driver = driver;
        this.route = route;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleItem that = (ScheduleItem) o;

        if (!driver.equals(that.driver)) return false;
        if (!route.equals(that.route)) return false;
        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        int result = driver.hashCode();
        result = 31 * result + route.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    public Driver getDriver() {

        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

}