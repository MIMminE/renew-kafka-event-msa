package nuts.dev.kafkamodule.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
@Validated
public class KafkaProducerProperties {

    @NotNull
    private String keySerializerClass;

    @NotNull
    private String valueSerializerClass;

    @NotNull
    private String compressionType;

    @NotNull
    private ACK_MODE ackMode;

    @NotNull
    private Integer batchSize;

    @NotNull
    private Integer requestTimeoutMs;

    @NotNull
    private Integer retryCount;

    @RequiredArgsConstructor
    @Getter
    enum ACK_MODE {
        ACKS_0("0"),
        ACKS_1("1"),
        ALL("all");

        private final String value;
    }
}
