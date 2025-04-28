package nuts.dev.elasticmodule.configuration;

import lombok.RequiredArgsConstructor;
import nuts.dev.elasticmodule.properties.ElasticProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
@RequiredArgsConstructor
@Import(ElasticProperties.class)
public class ElasticClientConfiguration extends ElasticsearchConfiguration {

    private final ElasticProperties elasticProperties;

    @Override
    public ClientConfiguration clientConfiguration() {
        System.out.println("ElasticClientConfiguration");

        return ClientConfiguration.builder()
                .connectedTo(elasticProperties.getConnectionUrl())
                .build();
    }
}