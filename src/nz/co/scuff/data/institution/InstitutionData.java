package nz.co.scuff.data.institution;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Callum on 3/06/2015.
 */
@Embeddable
public class InstitutionData implements Comparable, Serializable {

    @NotNull
    @Column(name="Name")
    private String name;
    @Column(name="Email")
    private String email;
    @Column(name="Phone")
    private String phone;

    public InstitutionData() {
    }

    public InstitutionData(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
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

        InstitutionData that = (InstitutionData) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return !(phone != null ? !phone.equals(that.phone) : that.phone != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object another) {
        InstitutionData that = (InstitutionData)another;
        if (that.name == null) return 1;
        if (this.name == null) return -1;
        return this.name.compareTo(that.name);
    }

    @Override
    public String toString() {
        return "InstitutionData{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
