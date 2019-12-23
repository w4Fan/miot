package cc.fans.miot.common.cache.config;

import cc.fans.miot.common.cache.model.Device;
import java.sql.Types;
import java.util.UUID;
import javax.sql.DataSource;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStore;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.BasicJdbcDialect;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceConfig {

  @Autowired
  private DataSource dataSource;

  public CacheConfiguration<UUID, Device> getDeviceCacheConfig() {
    CacheConfiguration<UUID, Device> cfg = new CacheConfiguration<>("DEVICES");
    CacheJdbcPojoStoreFactory<UUID, Device> factory = new CacheJdbcPojoStoreFactory<>();
    factory.setDataSource(dataSource);
    factory.setDialect(new BasicJdbcDialect());

    JdbcType jdbcType = new JdbcType();
    jdbcType.setCacheName("DEVICES");
    jdbcType.setDatabaseSchema("PUBLIC");
    jdbcType.setDatabaseTable("device");

    jdbcType.setKeyType(String.class);
    jdbcType.setKeyFields(new JdbcTypeField(Types.VARCHAR, "id", String.class, "id"));

    jdbcType.setValueType("cc.fans.miot.common.cache.model.Device");
    jdbcType.setValueFields(
        new JdbcTypeField(Types.VARCHAR, "id", String.class, "id"),
        new JdbcTypeField(Types.VARCHAR, "name", String.class, "name"),
        new JdbcTypeField(Types.VARCHAR, "type", String.class, "type")
    );
    factory.setTypes(jdbcType);
    cfg.setCacheStoreFactory(factory);
    cfg.setReadThrough(true);
    cfg.setWriteThrough(true);
    return cfg;
  }
}
