package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "student")
public class Student extends Person {
    private Double averageGrade;
    private String attendance;

    public Student() {
        super();
    }

    @Column(name = "average_grade")
    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Column(name = "attendance")
    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    @Override
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Course> getCourses() {
        return super.getCourses();
    }

    @Override
    public void setCourses(Set<Course> courses) {
        super.setCourses(courses);
    }
}
