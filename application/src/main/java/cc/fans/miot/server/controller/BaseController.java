package cc.fans.miot.server.controller;

import cc.fans.miot.common.cache.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

  @Autowired
  DeviceService deviceService;
}
