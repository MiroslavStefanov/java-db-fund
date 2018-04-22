package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "teacher")
public class Teacher extends Person {
    private String email;
    private BigDecimal salaryPerHour;

    public Teacher() {
        super();
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour")
    public BigDecimal getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(BigDecimal salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    @Override
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Course> getCourses() {
        return super.getCourses();
    }

    @Override
    public void setCourses(Set<Course> courses) {
        super.setCourses(courses);
    }
}
