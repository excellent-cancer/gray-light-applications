package gray.light.application.mountings.annotation;

import gray.light.application.mountings.WebExceptionHandlingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 应用服务器配置
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(WebExceptionHandlingConfiguration.class)
public @interface ApplicationServerMountings {
}
