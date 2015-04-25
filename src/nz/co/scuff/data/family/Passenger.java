package nz.co.scuff.data.family;

import nz.co.scuff.data.school.School;

/**
 * Created by Callum on 17/03/2015.
 */
public class Passenger extends Person {

    private School school;

    public Passenger() {}

    public Passenger(String name, School school, Gender gender, String pix) {
        super(name, gender, pix);
        this.school = school;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

}