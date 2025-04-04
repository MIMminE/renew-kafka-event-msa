package nuts.dev.streamingestor;

import lombok.RequiredArgsConstructor;
import model.StreamAvroModel;
import nuts.dev.kafkamodule.admin.KafkaAdminClient;
import nuts.dev.kafkamodule.annotation.EnableKafkaBroker;
import nuts.dev.kafkamodule.annotation.EnableKafkaProducer;
import nuts.dev.kafkamodule.producer.KafkaProducer;
import nuts.dev.kafkamodule.properties.KafkaBrokerProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
@EnableKafkaProducer
public class StreamIngestorApplication implements CommandLineRunner {

    private final KafkaBrokerProperties kafkaProperties;

    private final KafkaAdminClient kafkaAdminClient;

    private final KafkaProducer<Long, StreamAvroModel> kafkaProducer;


    public static void main(String[] args) {

        SpringApplication.run(StreamIngestorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
