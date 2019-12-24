package cc.fans.miot.common.cache.service;

import cc.fans.miot.common.cache.model.Device;
import java.util.List;
import java.util.UUID;

public interface DeviceService {

  List<Device> list(int page, int size);

  Device get(UUID id);

  Device insert(Device device);

  Device update(Device device);

  Boolean delete(UUID id);
}
