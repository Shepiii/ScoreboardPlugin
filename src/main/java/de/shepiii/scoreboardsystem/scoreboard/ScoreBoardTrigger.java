package de.shepiii.scoreboardsystem.scoreboard;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.shepiii.scoreboardsystem.config.ScoreBoardConfiguration;
import de.shepiii.scoreboardsystem.player.ScoreBoardPlayerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

@Singleton
public final class ScoreBoardTrigger implements Listener {
  private final ScoreBoardPlayerRegistry scoreBoardPlayerRegistry;
  private final ScoreBoardConfiguration scoreBoardConfiguration;

  @Inject
  private ScoreBoardTrigger(
    ScoreBoardPlayerRegistry scoreBoardPlayerRegistry,
    ScoreBoardConfiguration scoreBoardConfiguration
  ) {
    this.scoreBoardPlayerRegistry = scoreBoardPlayerRegistry;
    this.scoreBoardConfiguration = scoreBoardConfiguration;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent playerJoin) {
    Preconditions.checkNotNull(playerJoin);
    var player = playerJoin.getPlayer();
    var scoreBoardPlayer =
      scoreBoardPlayerRegistry
        .findByPlayer(player)
        .orElseThrow();
    var name = scoreBoardConfiguration.name();
    var title = scoreBoardConfiguration.title();
    Map<Integer, String> scores = scoreBoardConfiguration.scores();
    scoreBoardPlayer.sendScoreBoard(name, title, scores);
  }

}
