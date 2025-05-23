package nuts.dev.elasticmodule.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-search-config")
public class ElasticProperties {
    private String indexName;
    private String connectionUrl;
    private Integer connectTimeoutMs;
    private Integer socketTimeoutMs;
}

/**
 * twitter-index
 * http://localhost:9200
 * 5000
 * 30000
 */