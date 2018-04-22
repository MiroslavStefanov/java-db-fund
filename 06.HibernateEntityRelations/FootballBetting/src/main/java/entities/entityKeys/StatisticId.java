package entities.entityKeys;

import entities.Game;
import entities.Player;

import java.io.Serializable;
import java.util.Objects;

public class StatisticId implements Serializable {
    private Game game;
    private Player player;

    public StatisticId() {
    }

    public StatisticId(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        return 31*game.getId() + player.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatisticId id = (StatisticId)obj;
        return Objects.equals(this.game.getId(), id.game.getId()) && Objects.equals(this.player.getId(), id.player.getId());
    }
}
