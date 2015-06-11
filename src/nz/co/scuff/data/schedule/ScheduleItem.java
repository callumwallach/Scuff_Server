package nz.co.scuff.data.schedule;

import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.institution.Route;

import java.sql.Timestamp;

/**
 * Created by Callum on 17/03/2015.
 */
public class ScheduleItem {

    private Adult adult;
    private Route route;
    private Timestamp date;

    public ScheduleItem(Adult adult, Route route, Timestamp date) {
        this.adult = adult;
        this.route = route;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleItem that = (ScheduleItem) o;

        if (!adult.equals(that.adult)) return false;
        if (!route.equals(that.route)) return false;
        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        int result = adult.hashCode();
        result = 31 * result + route.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    public Adult getAdult() {

        return adult;
    }

    public void setAdult(Adult adult) {
        this.adult = adult;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

}