package cc.fans.miot.common.cache.config;

import cc.fans.miot.common.cache.model.Device;
import java.sql.Types;
import java.util.UUID;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStore;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.BasicJdbcDialect;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class DeviceConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public IgniteCache<UUID, Device> devices(@Autowired Ignite ignite) {
    return ignite.getOrCreateCache(deviceCacheConfig());
  }

  private CacheConfiguration<UUID, Device> deviceCacheConfig() {
    CacheConfiguration<UUID, Device> cfg = new CacheConfiguration<>("DEVICES");
    cfg.setIndexedTypes(UUID.class, Device.class);
    cfg.setReadThrough(true);
    cfg.setWriteThrough(true);

    CacheJdbcPojoStoreFactory<UUID, Device> factory = new CacheJdbcPojoStoreFactory<>();
    factory.setDataSource(dataSource);
    factory.setDialect(new BasicJdbcDialect());
    factory.setTypes(jdbcType());
    cfg.setCacheStoreFactory(factory);
    return cfg;
  }

  private JdbcType jdbcType() {
    JdbcType jdbcType = new JdbcType();
    jdbcType.setCacheName("DEVICES");
    jdbcType.setDatabaseSchema("PUBLIC");
    jdbcType.setDatabaseTable("device");

    jdbcType.setKeyType(UUID.class);
    jdbcType.setKeyFields(new JdbcTypeField(Types.VARCHAR, "id", UUID.class, "id"));

    jdbcType.setValueType("cc.fans.miot.common.cache.model.Device");
    jdbcType.setValueFields(
        new JdbcTypeField(Types.VARCHAR, "id", UUID.class, "id"),
        new JdbcTypeField(Types.VARCHAR, "name", String.class, "name"),
        new JdbcTypeField(Types.VARCHAR, "type", String.class, "type")
    );
    return jdbcType;
  }
}
