package de.shepiii.scoreboardsystem.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ScoreBoardConfiguration {
  private boolean enabled;
  private String title;
  private String name;
  private Map<Integer, String> scores;

  ScoreBoardConfiguration(boolean enabled, String title, String name, Map<Integer, String> scores) {
    this.enabled = enabled;
    this.title = title;
    this.name = name;
    this.scores = scores;
  }

  ScoreBoardConfiguration() {
  }

  @JsonProperty("enabled")
  public void enabled(boolean enabled) {
    this.enabled = enabled;
  }

  @JsonProperty("title")
  public void title(String title) {
    this.title = title;
  }

  @JsonProperty("name")
  public void name(String name) {
    this.name = name;
  }

  @JsonProperty("scores")
  public void scores(Map<Integer, String> scores) {
    this.scores = scores;
  }

  public boolean isEnabled() {
    return enabled;
  }

  @JsonProperty("title")
  public String title() {
    return title;
  }

  @JsonProperty("name")
  public String name() {
    return name;
  }

  @JsonProperty("scores")
  public Map<Integer, String> scores() {
    return scores;
  }
}
