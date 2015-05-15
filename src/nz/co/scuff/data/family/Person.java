package nz.co.scuff.data.family;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public abstract class Person implements Comparable, Serializable {

    public enum Gender {
        MALE, FEMALE
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="PersonId")
    protected long personId;
    @NotNull
    @Column(name="FirstName")
    protected String firstName;
    @Column(name="MiddleName")
    protected String middleName;
    @NotNull
    @Column(name="LastName")
    protected String lastName;
    @NotNull
    @Column(name="Gender")
    @Enumerated(EnumType.STRING)
    protected Gender gender;
    @Column(name="Picture")
    protected String picture;

    public Person() {}

    public Person(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }

    public Person(String firstName, String lastName, Gender gender, String picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.picture = picture;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long id) {
        this.personId = id;
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

        Person person = (Person) o;

        return personId == person.personId;

    }

    @Override
    public int hashCode() {
        return (int) (personId ^ (personId >>> 32));
    }

    @Override
    public int compareTo(Object another) {
        Person other = (Person)another;
        if (other.lastName == null) return 1;
        if (this.lastName == null) return -1;
        int lastNameCompared = this.lastName.compareTo(other.lastName);
        if (lastNameCompared != 0) return lastNameCompared;
        return this.firstName.compareTo(other.firstName);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", picture='").append(picture).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
