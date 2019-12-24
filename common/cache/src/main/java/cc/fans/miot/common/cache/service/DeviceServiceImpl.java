package cc.fans.miot.common.cache.service;

import cc.fans.miot.common.cache.model.Device;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.cache.Cache.Entry;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

  @Autowired
  private IgniteCache<UUID, Device> devices;

  @Override
  public List<Device> list(int page, int size) {
    SqlQuery<UUID, Device> sql = new SqlQuery<>(Device.class,
        "SELECT * FROM Device LIMIT ? OFFSET ?");
    QueryCursor<Entry<UUID, Device>> cursor = devices.query(sql.setArgs(size, page * size));
    return cursor.getAll().stream().map(Entry::getValue).collect(Collectors.toList());
  }

  @Override
  public Device get(UUID id) {
    return devices.get(id);
  }

  @Override
  public Device insert(Device device) {
    UUID id = UUID.randomUUID();
    device.setId(id);
    return devices.getAndPut(id, device);
  }

  @Override
  public Device update(Device device) {
    return devices.getAndPut(device.getId(), device);
  }

  @Override
  public Boolean delete(UUID id) {
    return devices.remove(id);
  }
}
