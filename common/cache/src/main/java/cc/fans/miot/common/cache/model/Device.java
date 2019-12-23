package cc.fans.miot.common.cache.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

@Data
public class Device implements Serializable {

  @QuerySqlField
  private UUID id;

  @QuerySqlField
  private String name;

  @QuerySqlField
  private String type;

  public Device() {
  }

  public Device(UUID id, String name, String type) {
    this.id = id;
    this.name = name;
    this.type = type;
  }
}
