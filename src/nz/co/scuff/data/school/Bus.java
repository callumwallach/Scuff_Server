package nz.co.scuff.data.school;

import java.util.HashSet;

import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.Parent;

/**
 * Created by Callum on 17/03/2015.
 */
public class Bus {

    private String name;
    private Parent parent;
    private HashSet<Child> children;

    public Bus(String name) {
        this.name = name;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public boolean addPassenger(Child child) {
        return this.children.add(child);
    }

    public boolean removePassenger(Child child) {
        return this.children.remove(child);
    }

    public HashSet<Child> getChildren() {
        return children;
    }

    public void setChildren(HashSet<Child> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}