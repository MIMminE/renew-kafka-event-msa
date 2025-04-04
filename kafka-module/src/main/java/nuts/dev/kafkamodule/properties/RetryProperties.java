package nuts.dev.kafkamodule.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-connection-retry")
@Validated
public class RetryProperties {

    @NotNull
    private Long initialIntervalMs;
    @NotNull
    private Long maxIntervalMs;
    @NotNull
    private Integer maxAttempts;
    @NotNull
    private Double multiplier;
    @NotNull
    private Long sleepTimeMs;
}
