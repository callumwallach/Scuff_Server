package nz.co.scuff.data.schedule;

import nz.co.scuff.data.family.Parent;
import nz.co.scuff.data.school.Route;

import java.sql.Timestamp;

/**
 * Created by Callum on 17/03/2015.
 */
public class ScheduleItem {

    private Parent parent;
    private Route route;
    private Timestamp date;

    public ScheduleItem(Parent parent, Route route, Timestamp date) {
        this.parent = parent;
        this.route = route;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleItem that = (ScheduleItem) o;

        if (!parent.equals(that.parent)) return false;
        if (!route.equals(that.route)) return false;
        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + route.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    public Parent getParent() {

        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
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