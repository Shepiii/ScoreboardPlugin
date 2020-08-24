package de.shepiii.scoreboardsystem.inject.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import javax.inject.Provider;

public final class ObjectMapperProvider implements Provider<ObjectMapper> {
  @Override
  public ObjectMapper get() {
    var yamlFactory = YAMLFactory.builder()
      .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
      .build();
    return new ObjectMapper(yamlFactory);
  }
}
