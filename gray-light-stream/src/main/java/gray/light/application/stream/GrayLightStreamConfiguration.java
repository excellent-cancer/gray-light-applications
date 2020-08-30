package gray.light.application.stream;

import gray.light.owner.annotation.DomainOwner;
import gray.light.owner.entity.ProjectStatus;
import gray.light.owner.service.ProjectDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@DomainOwner
@EnableBinding(SynchronizedBookSource.class)
@RequiredArgsConstructor
public class GrayLightStreamConfiguration {

    private final ProjectDetailsService projectDetailsService;

    private final SynchronizedBookSource synchronizedBookSource;

    /*@Bean
    public MessageSource<Object> bookMessageSource() {
        return new BookMessageSource(projectDetailsService);
    }*/

    @Bean(name = PollerMetadata.DEFAULT_POLLER_METADATA_BEAN_NAME)
    public PollerMetadata pollerMetadata() {
        PollerMetadata metadata = new PollerMetadata();
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(60, TimeUnit.SECONDS);
        metadata.setTrigger(periodicTrigger);

        return metadata;
    }

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.
                from(new BookMessageSource(projectDetailsService)).
                channel(synchronizedBookSource.output()).
                get();
    }

    @RequiredArgsConstructor
    static class BookMessageSource extends AbstractMessageSource<Object> {

        private final ProjectDetailsService projectDetailsService;

        @Override
        protected Object doReceive() {
            return projectDetailsService.findProjectDetailsByStatsList(Collections.singletonList(ProjectStatus.SYNC));
        }

        @Override
        public String getComponentType() {
            return "book:inbound-channel-adapter";
        }
    }

}
