package nuts.dev.kafkamodule.autoconfiguration;

import lombok.RequiredArgsConstructor;
import nuts.dev.kafkamodule.admin.KafkaAdminClient;
import nuts.dev.kafkamodule.properties.KafkaBrokerProperties;
import nuts.dev.kafkamodule.properties.RetryProperties;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Import({KafkaBrokerProperties.class, KafkaAdminClient.class, RetryProperties.class})
public class KafkaAdminClientAutoConfiguration {
    private final KafkaBrokerProperties brokerProperties;
    private final RetryProperties retryProperties;

    @Bean
    AdminClient adminClient() {
        try {
            return AdminClient.create(Map.of(
                    CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerProperties.getBootstrapServers()
            ));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Bean
    RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(retryProperties.getInitialIntervalMs());
        exponentialBackOffPolicy.setMaxInterval(retryProperties.getMaxIntervalMs());
        exponentialBackOffPolicy.setMultiplier(retryProperties.getMultiplier());

        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(retryProperties.getMaxAttempts());

        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        return retryTemplate;
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
}
