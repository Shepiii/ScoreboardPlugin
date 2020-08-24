package de.shepiii.scoreboardsystem.inject.provider;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ScoreBoardConfigurationPathProvider implements Provider<Path> {
  private final Path pluginPath;

  @Inject
  private ScoreBoardConfigurationPathProvider(@Named("pluginPath") Path pluginPath) {
    this.pluginPath = pluginPath;
  }

  @Override
  public Path get() {
    return Paths.get(pluginPath.toString(), "scoreboard.yml");
  }
}
