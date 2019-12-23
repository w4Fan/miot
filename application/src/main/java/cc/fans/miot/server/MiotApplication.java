package cc.fans.miot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("cc.fans.miot.common.cache")
@SpringBootApplication
public class MiotApplication {

  public static void main(String[] args) {
    SpringApplication.run(MiotApplication.class, args);
  }
}
