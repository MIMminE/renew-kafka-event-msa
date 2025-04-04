package nuts.dev.kafkamodule.annotation;

import nuts.dev.kafkamodule.autoconfiguration.KafkaAdminClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(KafkaAdminClientAutoConfiguration.class)
public @interface EnableKafkaBroker {
}
