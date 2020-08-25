package de.shepiii.scoreboardsystem.permission;

import com.google.inject.Inject;
import de.shepiii.scoreboardsystem.scoreboard.AsyncScoreBoardScoreCreate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;

public final class PermissionTrigger implements Listener {
  private final PermissionConfiguration permissionConfiguration;

  @Inject
  private PermissionTrigger(PermissionConfiguration permissionConfiguration) {
    this.permissionConfiguration = permissionConfiguration;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onScoreBoardScoreCreate(AsyncScoreBoardScoreCreate asyncScoreBoardScoreCreate) {
    var player = asyncScoreBoardScoreCreate.player();

    for (Map.Entry<String, String> permissionDisplayRankEntry
      : permissionConfiguration.getPermissionDisplayRankMap().entrySet()) {
      if (player.hasPermission(permissionDisplayRankEntry.getKey())) {
        asyncScoreBoardScoreCreate.replaceInDisplayContent(
          "%rank%", permissionDisplayRankEntry.getValue());
        return;
      }
    }
    asyncScoreBoardScoreCreate.replaceInDisplayContent(
      "%rank%", permissionConfiguration.getDefaultDisplayRank());

  }

}
