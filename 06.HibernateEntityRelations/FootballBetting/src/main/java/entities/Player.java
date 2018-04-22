package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    private Integer id;
    private String name;
    private Integer squadNumber;
    private Team team;
    private Position position;
    private Boolean isCurrentlyInjured;
    private Set<Game> games;
    private Set<PlayerStatistic> statistics;

    public Player() {
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

    @Column(name = "squad_number")
    public Integer getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(Integer squadNumber) {
        this.squadNumber = squadNumber;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Column(name = "is_currently_injured")
    public Boolean getCurrentlyInjured() {
        return isCurrentlyInjured;
    }

    public void setCurrentlyInjured(Boolean currentlyInjured) {
        isCurrentlyInjured = currentlyInjured;
    }

    @ManyToMany
    @JoinTable(name = "players_games",
        joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<PlayerStatistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(Set<PlayerStatistic> statistics) {
        this.statistics = statistics;
    }
}
