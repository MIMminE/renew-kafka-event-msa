package nuts.dev.elasticmodule.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(indexName = "#{elasticProperties.indexName}")
public class StreamIndexModel implements IndexModel{

    @JsonProperty
    private String id;

    @JsonProperty
    private Long userId;

    @JsonProperty
    private String text;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createAt;
}
