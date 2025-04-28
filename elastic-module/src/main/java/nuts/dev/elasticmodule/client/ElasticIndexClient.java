package nuts.dev.elasticmodule.client;

import nuts.dev.elasticmodule.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel>{
    List<String> save(List<T> documents);
}
