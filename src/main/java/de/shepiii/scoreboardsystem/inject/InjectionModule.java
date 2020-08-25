package de.shepiii.scoreboardsystem.inject;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.shepiii.scoreboardsystem.ScoreBoardSystemPlugin;
import de.shepiii.scoreboardsystem.config.ScoreBoardConfiguration;
import de.shepiii.scoreboardsystem.inject.provider.*;
import de.shepiii.scoreboardsystem.permission.PermissionConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.nio.file.Path;

public final class InjectionModule extends AbstractModule {
  private final ScoreBoardSystemPlugin plugin;

  private InjectionModule(ScoreBoardSystemPlugin scoreBoardSystemPlugin) {
    this.plugin = scoreBoardSystemPlugin;
  }

  @Override
  protected void configure() {
    bind(ObjectMapper.class)
      .toProvider(ObjectMapperProvider.class)
      .asEagerSingleton();
    bind(ProtocolManager.class).toInstance(ProtocolLibrary.getProtocolManager());
    bind(ScoreBoardSystemPlugin.class).toInstance(plugin);
    bind(PluginManager.class).toInstance(Bukkit.getPluginManager());
    bind(Path.class).annotatedWith(Names.named("pluginPath"))
      .toInstance(plugin.getDataFolder().toPath());
    bind(Path.class).annotatedWith(Names.named("scoreBoardConfigurationPath"))
      .toProvider(ScoreBoardConfigurationPathProvider.class)
      .asEagerSingleton();
    bind(ScoreBoardConfiguration.class).
      toProvider(ScoreBoardConfigurationProvider.class)
      .asEagerSingleton();
    bind(PermissionConfiguration.class)
      .toProvider(PermissionConfigurationProvider.class)
      .asEagerSingleton();
    bind(Path.class)
      .annotatedWith(Names.named("permissionConfigurationPath"))
      .toProvider(PermissionConfigurationPathProvider.class)
      .asEagerSingleton();
  }

  public static InjectionModule forPlugin(ScoreBoardSystemPlugin scoreBoardSystemPlugin) {
    return new InjectionModule(scoreBoardSystemPlugin);
  }

}
