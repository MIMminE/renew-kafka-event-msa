package nuts.dev.kafkatoelasticservice.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ElasticService {

    private final ElasticsearchProperties elasticsearchProperties;

//    private final ElasticsearchTemplate elasticsearchTemplate;

    private final ElasticsearchClient elasticsearchClient;
}
