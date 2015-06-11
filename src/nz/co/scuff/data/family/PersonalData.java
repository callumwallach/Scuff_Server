package nz.co.scuff.data.family;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Callum on 3/06/2015.
 */
@Embeddable
public class PersonalData implements Comparable, Serializable {

    public enum Gender {
        MALE, FEMALE
    }

    @NotNull
    @Column(name="FirstName")
    private String firstName;
    @Column(name="MiddleName")
    private String middleName;
    @NotNull
    @Column(name="LastName")
    private String lastName;
    @NotNull
    @Column(name="Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="Picture")
    private String picture;
    @Column(name="Email")
    private String email;
    @Column(name="Phone")
    private String phone;

    public PersonalData() {
    }

    public PersonalData(String firstName, String middleName, String lastName, Gender gender, String picture, String email, String phone) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.picture = picture;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalData that = (PersonalData) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (gender != that.gender) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return !(phone != null ? !phone.equals(that.phone) : that.phone != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object another) {
        PersonalData that = (PersonalData)another;
        if (that.lastName == null) return 1;
        if (this.lastName == null) return -1;
        int lastNameCompared = this.lastName.compareTo(that.lastName);
        if (lastNameCompared != 0) return lastNameCompared;
        return this.firstName.compareTo(that.firstName);
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
