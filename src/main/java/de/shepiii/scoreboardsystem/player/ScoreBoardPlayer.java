package de.shepiii.scoreboardsystem.player;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.shepiii.scoreboardsystem.packetwrapper.AbstractPacket;
import de.shepiii.scoreboardsystem.packetwrapper.WrapperPlayServerScoreboardDisplayObjective;
import de.shepiii.scoreboardsystem.packetwrapper.WrapperPlayServerScoreboardObjective;
import de.shepiii.scoreboardsystem.packetwrapper.WrapperPlayServerScoreboardScore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public final class ScoreBoardPlayer {
  private final Player player;

  private ScoreBoardPlayer(Player player) {
    this.player = player;
  }

  public void sendScoreBoard(
    String name, String title, Map<Integer, String> scores
  ) {
    sendScoreboardObjective(name, title);
    sendScoreBoardDisplayObjective(name);
    scores.forEach((value, score) -> sendScoreBoardScore(value, name, score));
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
    var scoreBoardScore = new WrapperPlayServerScoreboardScore();
    scoreBoardScore.setValue(value);
    scoreBoardScore.setObjectiveName(objectiveName);
    scoreBoardScore.setScoreName(ChatColor.translateAlternateColorCodes('&', score));
    scoreBoardScore.setScoreboardAction(EnumWrappers.ScoreboardAction.CHANGE);
    sendPacket(scoreBoardScore);
  }

  public void removeScoreBoard(String name) {
    var scoreboardDisplayObjectivePacket =
      new WrapperPlayServerScoreboardObjective();
    scoreboardDisplayObjectivePacket.setName(name);
    scoreboardDisplayObjectivePacket.setMode(1);
    sendPacket(scoreboardDisplayObjectivePacket);
  }

  public static ScoreBoardPlayer forPlayer(Player player) {
    return new ScoreBoardPlayer(player);
  }

  public UUID uniqueID() {
    return player.getUniqueId();
  }
}
