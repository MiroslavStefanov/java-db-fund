package entities.entityKeys;

import entities.Bet;
import entities.Game;

import java.io.Serializable;
import java.util.Objects;

public class BetGameId implements Serializable {
    private Game game;
    private Bet bet;

    public BetGameId() {
    }

    public BetGameId(Game game, Bet bet) {
        this.game = game;
        this.bet = bet;
    }

    public Game getGame() {
        return game;
    }

    public Bet getBet() {
        return bet;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @Override
    public int hashCode() {
        return 31*game.getId() + bet.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BetGameId id = (BetGameId)obj;
        return Objects.equals(this.game.getId(), id.game.getId()) && Objects.equals(this.bet.getId(), id.bet.getId());
    }
}
