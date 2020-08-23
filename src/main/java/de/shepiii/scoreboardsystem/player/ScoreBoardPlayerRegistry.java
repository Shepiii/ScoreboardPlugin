package de.shepiii.scoreboardsystem.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public final class ScoreBoardPlayerRegistry {
  private final Map<UUID, ScoreBoardPlayer> scoreBoardPlayers = Maps.newHashMap();

  public void addScoreBoardPlayer(ScoreBoardPlayer scoreBoardPlayer) {
    Preconditions.checkNotNull(scoreBoardPlayer);
    scoreBoardPlayers.put(scoreBoardPlayer.uniqueID(), scoreBoardPlayer);
  }

  public void removeScoreBoardPlayer(ScoreBoardPlayer scoreBoardPlayer) {
    Preconditions.checkNotNull(scoreBoardPlayer);
    scoreBoardPlayers.remove(scoreBoardPlayer.uniqueID());
  }

  public Optional<ScoreBoardPlayer> findByPlayer(Player player) {
    return Optional.ofNullable(scoreBoardPlayers.get(player.getUniqueId()));
  }
}
