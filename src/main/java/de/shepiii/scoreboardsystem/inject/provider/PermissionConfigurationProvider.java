package de.shepiii.scoreboardsystem.inject.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.shepiii.scoreboardsystem.permission.PermissionConfiguration;

import javax.inject.Provider;
import java.io.IOException;
import java.nio.file.Path;

public final class PermissionConfigurationProvider implements Provider<PermissionConfiguration> {
  private final Path path;
  private final ObjectMapper objectMapper;

  @Inject
  private PermissionConfigurationProvider(@Named("permissionConfigurationPath") Path path, ObjectMapper objectMapper) {
    this.path = path;
    this.objectMapper = objectMapper;
  }

  @Override
  public PermissionConfiguration get() {
    try {
      return objectMapper.readValue(
        path.toFile(),
        PermissionConfiguration.class
      );
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
