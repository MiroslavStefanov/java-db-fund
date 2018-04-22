package entities;

import javax.persistence.*;

enum Prediction {HomeTeamWins, DrawGame, AwayTeamWins};

@Entity
@Table(name = "result_predictions")
public class ResultPrediction {
    private Integer id;
    private Prediction prediction;

    public ResultPrediction() {
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "prediction")
    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }
}
