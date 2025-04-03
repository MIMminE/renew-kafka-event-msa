package nuts.dev.kafkamodule.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-broker")
@Validated
public class KafkaBrokerProperties {
    @NotNull
    private String bootstrapServers;

    @NotNull
    private String schemaRegistryUrl;

    @NotNull
    private String schemaRegistryUrlKey;

    @NotNull
    private String topicName;

    @NotNull
    private Integer numOfPartitions;

    @NotNull
    private Short replicationFactor;
}