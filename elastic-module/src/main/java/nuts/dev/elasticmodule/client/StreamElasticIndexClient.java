package nuts.dev.elasticmodule.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.dev.elasticmodule.model.StreamIndexModel;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class StreamElasticIndexClient implements ElasticIndexClient<StreamIndexModel> {

    private final String indexName;
    private final ElasticsearchOperations operations;

    @Override
    public List<String> save(List<StreamIndexModel> documents) {
        List<IndexQuery> indexQueries = ElasticIndexUtil.getIndexQueries(documents);
        List<IndexedObjectInformation> indexedObjects = operations.bulkIndex(indexQueries, IndexCoordinates.of(indexName));
        log.info("Bulk Indexed Documents: {}", indexedObjects);
        return indexedObjects.stream().map(IndexedObjectInformation::id).toList();
    }

}
