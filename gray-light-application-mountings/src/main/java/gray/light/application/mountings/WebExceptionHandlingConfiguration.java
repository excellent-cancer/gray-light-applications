package gray.light.application.mountings;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import perishing.constraint.web.KnownBusinessException;
import perishing.constraint.web.flux.ResponseBuffet;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置关于运行时异常捕捉如何反馈至客户端
 *
 * @author XyParaCrim
 */
public class WebExceptionHandlingConfiguration {

    /**
     * 最高优先级，捕捉已知Exception
     *
     * @return web异常处理器
     */
    @Bean
    @Order(Integer.MIN_VALUE)
    public WebExceptionHandler knownWebExceptionHandler(ServerCodecConfigurer serverCodecConfigurer,
                                                        ObjectProvider<ViewResolver> viewResolvers) {
        return new WebKnownExceptionHandler(serverCodecConfigurer.getWriters(),
                viewResolvers.orderedStream().collect(Collectors.toList()));
    }


    @RequiredArgsConstructor
    private static class WebKnownExceptionHandler implements WebExceptionHandler, ServerResponse.Context {

        private final List<HttpMessageWriter<?>> messageWriters;

        private final List<ViewResolver> viewResolvers;

        /**
         * 拦截{@link KnownBusinessException}异常，其他异常意为未知异常，交给其他处理器
         * 解决
         *
         * @param exchange 请求回复
         * @param ex 异常
         * @return 发布者
         */
        @NonNull
        @Override
        public Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {
            return ex instanceof KnownBusinessException ?
                    ResponseBuffet.failByKnownException((KnownBusinessException) ex).
                            flatMap(serverResponse -> serverResponse.writeTo(exchange, this)) :
                    Mono.error(ex);
        }

        @NonNull
        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return messageWriters;
        }

        @NonNull
        @Override
        public List<ViewResolver> viewResolvers() {
            return viewResolvers;
        }
    }

}
