package nz.co.scuff.data.family;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Callum on 3/06/2015.
 */
@Embeddable
public class ChildData implements Comparable, Serializable {

    public enum Gender {
        MALE(0), FEMALE(1);

        private final int value;

        private Gender(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    @Column(name="FirstName")
    private String firstName;
    @Column(name="MiddleName")
    private String middleName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="Gender")
    private Gender gender;
    @Column(name="Picture")
    private String picture;

    public ChildData() {
    }

    public ChildData(String firstName, String middleName, String lastName, Gender gender, String picture) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.picture = picture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChildData that = (ChildData) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (gender != that.gender) return false;

        return true;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object another) {
        ChildData that = (ChildData)another;
        if (that.lastName == null) return 1;
        if (this.lastName == null) return -1;
        int lastNameCompared = this.lastName.compareTo(that.lastName);
        if (lastNameCompared != 0) return lastNameCompared;
        return this.firstName.compareTo(that.firstName);
    }

    @Override
    public String toString() {
        return "ChildData{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", picture='" + picture + '\'' +
                '}';
    }

}
