package de.shepiii.scoreboardsystem.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncScoreBoardScoreCreate extends Event implements Cancellable {
  private static final HandlerList HANDLERS_LIST = new HandlerList();
  private final Player player;
  private final int score;
  private boolean cancelled;
  private String displayContent;


  public AsyncScoreBoardScoreCreate(final boolean async, Player player, int score, String displayContent) {
    super(async);
    this.player = player;
    this.score = score;
    this.displayContent = displayContent;
    this.cancelled = false;
  }

  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public void displayContent(String displayContent) {
    this.displayContent = displayContent;
  }

  /**
   * Replaces each substring of this string that matches the literal target
   * sequence with the specified literal replacement sequence. The
   * replacement proceeds from the beginning of the string to the end, for
   * example, replacing "aa" with "b" in the string "aaa" will result in
   * "ba" rather than "ab".
   *
   * @param toReplace   The sequence of char values to be replaced
   * @param replacement The replacement sequence of char values
   */
  public void replaceInDisplayContent(String toReplace, String replacement) {
    displayContent = displayContent.replace(toReplace, replacement);
  }

  public Player player() {
    return player;
  }

  public int score() {
    return score;
  }

  public String displayContent() {
    return displayContent;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS_LIST;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS_LIST;
  }
}
