package de.shepiii.scoreboardsystem;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.shepiii.scoreboardsystem.config.ScoreBoardConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import javax.inject.Named;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ScoreBoardSystemModule extends AbstractModule {
  private final Plugin plugin;
  private final Path scoreBoardConfigurationPath;

  private ScoreBoardSystemModule(Plugin plugin, Path scoreBoardConfigurationPath) {
    this.plugin = plugin;
    this.scoreBoardConfigurationPath = scoreBoardConfigurationPath;
  }

  @Singleton
  @Provides
  Plugin providePlugin() {
    return plugin;
  }

  @Provides
  @Singleton
  PluginManager providePluginManager() {
    return Bukkit.getPluginManager();
  }

  @Provides
  @Singleton
  ProtocolManager provideProtocolManager() {
    return ProtocolLibrary.getProtocolManager();
  }

  @Provides
  @Singleton
  YAMLFactory provideYamlFactory() {
    return YAMLFactory.builder()
      .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
      .build();
  }

  @Provides
  @Singleton
  ObjectMapper provideObjectMapper(YAMLFactory yamlFactory) {
    return new ObjectMapper(yamlFactory);
  }

  @Singleton
  @Provides
  @Named("scoreBoardConfigurationPath")
  Path provideScoreBoardConfigurationPath() {
    return scoreBoardConfigurationPath;
  }

  @Provides
  @Singleton
  ScoreBoardConfiguration provideScoreBoardConfiguration(
    ObjectMapper objectMapper,
    @Named("scoreBoardConfigurationPath") Path provideScoreBoardConfigurationPath
  ) throws Exception {
    return objectMapper.readValue(
      provideScoreBoardConfigurationPath.toFile(),
      ScoreBoardConfiguration.class
    );
  }

  public static ScoreBoardSystemModule forPlugin(Plugin plugin) {
    var pluginPath = plugin.getDataFolder().getPath();
    var scoreBoardConfigurationPath = Paths.get(pluginPath, "scoreboard.yml");
    return new ScoreBoardSystemModule(plugin, scoreBoardConfigurationPath);
  }
}
