package nuts.dev.kafkatoelasticservice;

import lombok.RequiredArgsConstructor;
import nuts.dev.elasticmodule.annotation.EnableElasticClient;
import nuts.dev.kafkamodule.annotation.EnableKafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
//@EnableKafkaConsumer
//@EnableElasticClient
public class KafkaToElasticServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticServiceApplication.class, args);
    }

}
