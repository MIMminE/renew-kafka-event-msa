package nuts.dev.kafkamodule.annotation;

import nuts.dev.kafkamodule.configuration.EnableKafkaProducerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EnableKafkaProducerConfiguration.class)
@EnableKafkaBroker
public @interface EnableKafkaProducer {
}
