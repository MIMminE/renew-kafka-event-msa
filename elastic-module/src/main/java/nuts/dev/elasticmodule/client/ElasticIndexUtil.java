package nuts.dev.elasticmodule.client;

import nuts.dev.elasticmodule.model.IndexModel;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ElasticIndexUtil {

    public static <T extends IndexModel> List<IndexQuery> getIndexQueries(List<T> documents) {
        return documents.stream()
                .map(document -> new IndexQueryBuilder()
                        .withId(document.getId())
                        .withObject(document)
                        .build()
                ).collect(Collectors.toList());
    }
}
