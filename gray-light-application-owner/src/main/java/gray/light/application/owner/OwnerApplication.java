package gray.light.application.owner;

import gray.light.application.mountings.annotation.ApplicationServerMountings;
import gray.light.owner.annotation.DomainOwner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Owner服务应用启动入口
 *
 * @author XyParaCrim
 */
@DomainOwner
@SpringBootApplication
@ApplicationServerMountings
public class OwnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnerApplication.class, args);
    }

}
