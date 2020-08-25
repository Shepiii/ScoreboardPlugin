package de.shepiii.scoreboardsystem.player;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.shepiii.scoreboardsystem.packetwrapper.AbstractPacket;
import de.shepiii.scoreboardsystem.packetwrapper.WrapperPlayServerScoreboardDisplayObjective;
import de.shepiii.scoreboardsystem.packetwrapper.WrapperPlayServerScoreboardObjective;
import de.shepiii.scoreboardsystem.packetwrapper.WrapperPlayServerScoreboardScore;
import de.shepiii.scoreboardsystem.scoreboard.AsyncScoreBoardScoreCreate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ScoreBoardPlayer {
  private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

  private final Player player;

  private ScoreBoardPlayer(Player player) {
    this.player = player;
  }

  public void sendScoreBoard(
    String name, String title, Map<Integer, String> scores) {
    sendScoreboardObjective(name, title);
    sendScoreBoardDisplayObjective(name);
    EXECUTOR_SERVICE.execute(() ->
      scores.forEach((value, score) -> sendScoreBoardScore(value, name, score)));
  }

  public void removeScoreBoard(String name) {
    var scoreboardDisplayObjectivePacket =
      new WrapperPlayServerScoreboardObjective();
    scoreboardDisplayObjectivePacket.setName(name);
    scoreboardDisplayObjectivePacket.setMode(1);
    sendPacket(scoreboardDisplayObjectivePacket);
  }


  private void sendPacket(AbstractPacket abstractPacket) {
    abstractPacket.sendPacket(player);
  }

  private void sendScoreboardObjective(String name, String title) {
    var scoreboardDisplayObjectivePacket =
      new WrapperPlayServerScoreboardObjective();
    scoreboardDisplayObjectivePacket.setName(name);
    scoreboardDisplayObjectivePacket.setDisplayName(
      WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', title))
    );
    scoreboardDisplayObjectivePacket.setHealthDisplay(
      WrapperPlayServerScoreboardObjective.HealthDisplay.INTEGER)
    ;
    scoreboardDisplayObjectivePacket.setMode(0);
    sendPacket(scoreboardDisplayObjectivePacket);
  }

  private void sendScoreBoardDisplayObjective(String name) {
    var scoreboardDisplayObjective
      = new WrapperPlayServerScoreboardDisplayObjective();
    scoreboardDisplayObjective.setPosition(1);
    scoreboardDisplayObjective.setScoreName(name);
    sendPacket(scoreboardDisplayObjective);
  }

  private void sendScoreBoardScore(int value, String objectiveName, String score) {
    var scoreBoardScoreCreate = callScoreBoardScoreCreate(value, score);
    if (scoreBoardScoreCreate.isCancelled()) {
      return;
    }
    var scoreBoardScore = fromObjectiveName(value, objectiveName);
    scoreBoardScore.setScoreName(
      ChatColor.translateAlternateColorCodes(
        '&', scoreBoardScoreCreate.displayContent())
    );
    sendPacket(scoreBoardScore);

  }

  private WrapperPlayServerScoreboardScore fromObjectiveName(int score, String objectiveName) {
    var scoreBoardScore = new WrapperPlayServerScoreboardScore();
    scoreBoardScore.setObjectiveName(objectiveName);
    scoreBoardScore.setScoreboardAction(EnumWrappers.ScoreboardAction.CHANGE);
    scoreBoardScore.setValue(score);
    return scoreBoardScore;
  }

  private AsyncScoreBoardScoreCreate callScoreBoardScoreCreate(int score, String displayContent) {
    var scoreBoardScoreCreate = new AsyncScoreBoardScoreCreate(true, player, score, displayContent);
    Bukkit.getPluginManager().callEvent(scoreBoardScoreCreate);
    return scoreBoardScoreCreate;
  }

  public static ScoreBoardPlayer forPlayer(Player player) {
    return new ScoreBoardPlayer(player);
  }

  public UUID uniqueID() {
    return player.getUniqueId();
  }
}
