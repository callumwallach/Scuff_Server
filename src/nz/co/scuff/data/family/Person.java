package nz.co.scuff.data.family;

/**
 * Created by Callum on 17/03/2015.
 */
public abstract class Person {

    public enum Gender {
        MALE, FEMALE
    }

    private String name;
    private String pix;
    private Gender gender;

    public Person() {}

    public Person(String name, Gender gender, String pix) {
        this.name = name;
        this.gender = gender;
        this.pix = pix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

    @Override
    public String toString() {
        return name;
    }

}
