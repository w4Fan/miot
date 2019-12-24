package cc.fans.miot.server.controller;

import cc.fans.miot.common.cache.model.Device;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeviceController extends BaseController {

  @PostMapping(value = "/devices")
  public Device saveDevice(@RequestBody Device device) {
    return deviceService.insert(device);
  }

  @GetMapping(value = "/devices")
  public List<Device> getDeviceList(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size) {
    if (page == null) {
      page = 0;
    }
    if (size == null) {
      size = 10;
    }
    return deviceService.list(page, size);
  }
}
