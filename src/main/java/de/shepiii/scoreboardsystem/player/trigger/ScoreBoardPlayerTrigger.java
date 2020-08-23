package de.shepiii.scoreboardsystem.player.trigger;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.shepiii.scoreboardsystem.player.ScoreBoardPlayer;
import de.shepiii.scoreboardsystem.player.ScoreBoardPlayerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Singleton
public final class ScoreBoardPlayerTrigger implements Listener {
  private final ScoreBoardPlayerRegistry scoreBoardPlayerRegistry;

  @Inject
  private ScoreBoardPlayerTrigger(ScoreBoardPlayerRegistry scoreBoardPlayerRegistry) {
    this.scoreBoardPlayerRegistry = scoreBoardPlayerRegistry;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent playerJoin) {
    Preconditions.checkNotNull(playerJoin);
    var player = playerJoin.getPlayer();
    var scoreBoardPlayer = ScoreBoardPlayer.forPlayer(player);
    scoreBoardPlayerRegistry.addScoreBoardPlayer(scoreBoardPlayer);
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerQuit(PlayerQuitEvent playerQuit) {
    Preconditions.checkNotNull(playerQuit);
    var player = playerQuit.getPlayer();
    var scoreBoardPlayer = scoreBoardPlayerRegistry.findByPlayer(player);
    scoreBoardPlayer.ifPresent(scoreBoardPlayerRegistry::removeScoreBoardPlayer);
  }
}
