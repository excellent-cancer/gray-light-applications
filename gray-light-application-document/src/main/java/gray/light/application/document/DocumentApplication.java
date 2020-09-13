package gray.light.application.document;

import gray.light.application.mountings.annotation.ApplicationServerMountings;
import gray.light.document.annotation.DomainDocument;
import gray.light.owner.client.OwnerServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import reactivefeign.spring.config.EnableReactiveFeignClients;

/**
 * Document服务应用启动入口
 *
 * @author XyParaCrim
 */
@EnableReactiveFeignClients(basePackageClasses = OwnerServiceClient.class)
@EnableFeignClients(basePackageClasses = OwnerServiceClient.class)
@DomainDocument
@SpringBootApplication
@ApplicationServerMountings
public class DocumentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentApplication.class, args);
    }

}
