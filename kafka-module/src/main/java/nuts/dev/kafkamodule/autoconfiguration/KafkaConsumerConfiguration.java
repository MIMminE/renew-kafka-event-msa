package nuts.dev.kafkamodule.autoconfiguration;


import lombok.RequiredArgsConstructor;
import model.StreamAvroModel;
import nuts.dev.kafkamodule.properties.KafkaBrokerProperties;
import nuts.dev.kafkamodule.properties.KafkaConsumerProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@Import({KafkaBrokerProperties.class, KafkaConsumerProperties.class})
public class KafkaConsumerConfiguration {

    private final KafkaBrokerProperties brokerProperties;
    private final KafkaConsumerProperties consumerProperties;

    @Bean
    ConsumerFactory<Long, StreamAvroModel> consumerFactory() {

        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerProperties.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerProperties.getValueDeserializer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.getConsumerGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProperties.getAutoOffsetReset());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerProperties.getSessionTimeoutMs());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, consumerProperties.getHeartbeatIntervalMs());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, consumerProperties.getMaxPollIntervalMs());
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, consumerProperties.getMaxPartitionFetchBytesDefault() *
                consumerProperties.getMaxPartitionFetchBytesBoostFactor());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerProperties.getMaxPollRecords());
        props.put(brokerProperties.getSchemaRegistryUrlKey(), brokerProperties.getSchemaRegistryUrl());
        props.put(consumerProperties.getSpecificAvroReaderKey(), consumerProperties.getSpecificAvroReader());

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long, StreamAvroModel>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, StreamAvroModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(consumerProperties.getBatchListener());
        factory.setConcurrency(consumerProperties.getConcurrencyLevel());
        factory.setAutoStartup(consumerProperties.getAutoStartUp());
        factory.getContainerProperties().setPollTimeout(consumerProperties.getPollTimeoutMs());
        return factory;
    }
}
