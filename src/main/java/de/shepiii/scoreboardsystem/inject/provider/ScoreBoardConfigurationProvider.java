package de.shepiii.scoreboardsystem.inject.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.shepiii.scoreboardsystem.config.ScoreBoardConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.io.IOException;
import java.nio.file.Path;

public final class ScoreBoardConfigurationProvider implements Provider<ScoreBoardConfiguration> {
  private final Path path;
  private final ObjectMapper objectMapper;

  @Inject
  private ScoreBoardConfigurationProvider(
    @Named("scoreBoardConfigurationPath") Path path, ObjectMapper objectMapper) {
    this.path = path;
    this.objectMapper = objectMapper;
  }

  @Override
  public ScoreBoardConfiguration get() {
    try {
      return objectMapper.readValue(
        path.toFile(),
        ScoreBoardConfiguration.class
      );
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
