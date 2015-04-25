package nz.co.scuff.data.school;

/**
 * Created by Callum on 17/03/2015.
 */
public class Route {

    private String name;
    private Bus bus;
    private String routeMap;

    public Route(String name) {
        this.name = name;
        this.bus = new Bus(name);
        this.routeMap = "Currently not available";
    }

    public Route(String name, String routeMap) {
        this.name = name;
        this.bus = new Bus(name);
        this.routeMap = routeMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getRouteMap() {
        return routeMap;
    }

    public void setRouteMap(String routeMap) {
        this.routeMap = routeMap;
    }

    @Override
    public String toString() {
        return name;
    }

}