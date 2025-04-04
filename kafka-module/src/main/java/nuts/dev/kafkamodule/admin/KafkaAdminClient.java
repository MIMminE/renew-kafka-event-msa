package nuts.dev.kafkamodule.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.dev.kafkamodule.exception.KafkaClientException;
import nuts.dev.kafkamodule.properties.KafkaBrokerProperties;
import nuts.dev.kafkamodule.properties.RetryProperties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaAdminClient {

    private final KafkaBrokerProperties brokerProperties;

    private final RetryProperties retryProperties;

    private final AdminClient adminClient;

    private final WebClient webClient;

    private final RetryTemplate retryTemplate;


    public void createTopics() {
        CreateTopicsResult createTopicsResult = retryTemplate.execute(this::doCreateTopics);

    }

    public void checkSchemaRegistry() {
        int retryCount = 1;
        Integer maxRetry = retryProperties.getMaxAttempts();
        int multiplier = retryProperties.getMultiplier().intValue();
        Long sleepTimeMs = retryProperties.getSleepTimeMs();

        while (!getSchemaRegistryStatus().is2xxSuccessful()) {
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTimeMs);
            sleepTimeMs *= multiplier;
        }
    }

    private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
        try {

            String topicName = brokerProperties.getTopicName();
            log.info("Creating topic {}, attempt {}", topicName, retryContext.getRetryCount());

            NewTopic newTopic = new NewTopic(topicName, brokerProperties.getNumOfPartitions(), brokerProperties.getReplicationFactor());
            return adminClient.createTopics(List.of(newTopic));

        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for creating kafka topic(s)!", t);
            // 재시도 횟수만큼 재시도를 했지만 그럼에도 카프카 토픽 생성에 실패할 경우 발생하는 예외
        }
    }

    private HttpStatusCode getSchemaRegistryStatus() {
        try {
            return webClient
                    .method(HttpMethod.GET)
                    .uri(brokerProperties.getSchemaRegistryUrl())
                    .retrieve().toBodilessEntity()
                    .map(ResponseEntity::getStatusCode)
                    .block();
        } catch (Exception e) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }

    private void checkMaxRetry(int retry, Integer maxRetry) {
        if (retry > maxRetry) {
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!");
        }
    }

    private void sleep(Long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error while sleeping for waiting new created topics!!");
        }
    }
}
