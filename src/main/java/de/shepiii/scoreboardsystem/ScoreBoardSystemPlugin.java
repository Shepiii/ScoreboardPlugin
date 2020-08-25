package de.shepiii.scoreboardsystem;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.shepiii.scoreboardsystem.config.ScoreBoardConfiguration;
import de.shepiii.scoreboardsystem.inject.InjectionModule;
import de.shepiii.scoreboardsystem.permission.PermissionTrigger;
import de.shepiii.scoreboardsystem.player.ScoreBoardPlayer;
import de.shepiii.scoreboardsystem.player.ScoreBoardPlayerRegistry;
import de.shepiii.scoreboardsystem.player.trigger.ScoreBoardPlayerTrigger;
import de.shepiii.scoreboardsystem.scoreboard.ScoreBoardTrigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.Executors;

public final class ScoreBoardSystemPlugin extends JavaPlugin {
  private Injector injector;
  @Inject
  private PluginManager pluginManager;
  @Inject
  private ScoreBoardConfiguration scoreBoardConfiguration;
  @Inject
  private ScoreBoardPlayerRegistry scoreBoardPlayerRegistry;

  @Override
  public void onEnable() {
    saveResources();
    injector = Guice.createInjector(InjectionModule.forPlugin(this));
    injector.injectMembers(this);
    if (!scoreBoardConfiguration.isEnabled()) {
      Bukkit.getConsoleSender().sendMessage("Plugin is disabled!");
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }
    registerTriggers();
    if (Bukkit.getOnlinePlayers().isEmpty()) {
      updatePlayers();
    }
  }

  private void saveResources() {
    saveResource("scoreboard.yml", false);
    saveResource("permissionConfiguration.yml", false);
  }

  private void registerTriggers() {
    pluginManager.registerEvents(injector.getInstance(ScoreBoardTrigger.class), this);
    pluginManager.registerEvents(injector.getInstance(ScoreBoardPlayerTrigger.class), this);
    pluginManager.registerEvents(injector.getInstance(PermissionTrigger.class), this);
  }

  private void updatePlayers() {
    Executors.newSingleThreadExecutor().execute(() -> {
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        var scoreBoardPlayer = ScoreBoardPlayer.forPlayer(onlinePlayer);
        var name = scoreBoardConfiguration.name();
        var title = scoreBoardConfiguration.title();
        Map<Integer, String> scores = scoreBoardConfiguration.scores();
        scoreBoardPlayer.removeScoreBoard(name);
        scoreBoardPlayerRegistry.addScoreBoardPlayer(scoreBoardPlayer);
        scoreBoardPlayer.sendScoreBoard(name, title, scores);
      }
    });
  }
}
