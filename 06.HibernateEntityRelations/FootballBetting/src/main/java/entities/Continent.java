package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "continents")
public class Continent {
    private Integer id;
    private String name;
    private Set<Country> countries;

    public Continent() {
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "continents", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
