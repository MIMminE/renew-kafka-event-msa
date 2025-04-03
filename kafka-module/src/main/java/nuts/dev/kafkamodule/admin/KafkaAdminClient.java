package nuts.dev.kafkamodule.admin;


import lombok.RequiredArgsConstructor;
import nuts.dev.kafkamodule.model.StreamAvroModel;
import nuts.dev.kafkamodule.properties.KafkaBrokerProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaAdminClient {

    private final KafkaBrokerProperties brokerProperties;

    private StreamAvroModel streamAvroModel;

}
