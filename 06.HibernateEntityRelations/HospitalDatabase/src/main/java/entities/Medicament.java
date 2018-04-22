package entities;

import javax.persistence.*;

@Entity
@Table(name = "medicaments")
public class Medicament {
    private Integer id;
    private String name;

    public Medicament() {
    }

    public Medicament(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
