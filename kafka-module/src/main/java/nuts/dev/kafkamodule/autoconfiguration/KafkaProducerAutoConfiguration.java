package nuts.dev.kafkamodule.autoconfiguration;

import lombok.RequiredArgsConstructor;
import model.StreamAvroModel;
import nuts.dev.kafkamodule.producer.KafkaProducer;
import nuts.dev.kafkamodule.producer.impl.DefaultStreamAvroKafkaProducer;
import nuts.dev.kafkamodule.properties.KafkaBrokerProperties;
import nuts.dev.kafkamodule.properties.KafkaProducerProperties;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.checkerframework.checker.units.qual.K;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Import({KafkaAdminClientAutoConfiguration.class, KafkaProducerProperties.class, KafkaProducer.class})
public class KafkaProducerAutoConfiguration {
    private final KafkaBrokerProperties kafkaBrokerProperties;
    private final KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerProperties.getBootstrapServers());
        props.put(kafkaBrokerProperties.getSchemaRegistryUrlKey(), kafkaBrokerProperties.getSchemaRegistryUrl());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getKeySerializerClass());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getValueSerializerClass());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerProperties.getCompressionType());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerProperties.getRequestTimeoutMs());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerProperties.getRetryCount());
        return props;
    }

    @Bean
    public ProducerFactory<Long, StreamAvroModel> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<Long, StreamAvroModel> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
