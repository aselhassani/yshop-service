package com.yoozlab.yshop.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCompressor;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsCommandListener;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
@RequiredArgsConstructor
public class MongoConfiguration extends AbstractMongoClientConfiguration {

  private final MeterRegistry meterRegistry;

  @Value("${spring.data.mongodb.username}")
  private String username;
  @Value("${spring.data.mongodb.password}")
  private String password;
  @Value("${spring.data.mongodb.database}")
  private String database;
  @Value("${spring.data.mongodb.authentication-database}")
  private String authenticationDatabase;
  @Value("${spring.data.mongodb.host}")
  private String host;
  @Value("${spring.data.mongodb.port}")
  private Integer port;

  @Value("${spring.data.mongodb.ssl.enabled}")
  private Boolean sslEnabled;

  @Override
  protected boolean autoIndexCreation() {
    return true;
  }

  @Override
  @NonNull
  protected String getDatabaseName() {
    return database;
  }

  @Override
  protected void configureClientSettings(MongoClientSettings.Builder builder) {
    if (!username.isEmpty()) {
      builder.credential(MongoCredential.createCredential(username, authenticationDatabase,
          password.toCharArray()));
    }

    builder.compressorList(List.of(MongoCompressor.createZlibCompressor()));
    builder.addCommandListener(new MongoMetricsCommandListener(meterRegistry));
    builder.uuidRepresentation(UuidRepresentation.STANDARD).codecRegistry(codecRegistries());
    builder.applyToClusterSettings(clusterSettingBuilder ->
        clusterSettingBuilder.hosts(List.of(new ServerAddress(host, port))));

    builder.applyToSslSettings(sslBuilder -> sslBuilder.enabled(sslEnabled));

    // documentDB compatibility settings
    // Notice: https://docs.aws.amazon.com/documentdb/latest/developerguide/functional-differences.html#functional-differences.retryable-writes
    builder.retryWrites(false);
  }

  private CodecRegistry codecRegistries() {
    return CodecRegistries.fromRegistries(
        CodecRegistries.fromProviders(new UuidCodecProvider(UuidRepresentation.STANDARD)),
        MongoClientSettings.getDefaultCodecRegistry());
  }

}
