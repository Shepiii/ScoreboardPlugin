package de.shepiii.scoreboardsystem.permission;

import java.util.Map;

public final class PermissionConfiguration {
  private Map<String, String> permissionDisplayRankMap;
  private String defaultDisplayRank;

  PermissionConfiguration(Map<String, String> permissionDisplayRankMap) {
    this.permissionDisplayRankMap = permissionDisplayRankMap;
  }

  PermissionConfiguration() {
  }

  public void setDefaultDisplayRank(String defaultDisplayRank) {
    this.defaultDisplayRank = defaultDisplayRank;
  }

  public void setPermissionDisplayRankMap(Map<String, String> permissionDisplayRankMap) {
    this.permissionDisplayRankMap = permissionDisplayRankMap;
  }

  public Map<String, String> getPermissionDisplayRankMap() {
    return permissionDisplayRankMap;
  }

  public String getDefaultDisplayRank() {
    return defaultDisplayRank;
  }
}
