package nuts.dev.elasticmodule.annotation;

import nuts.dev.elasticmodule.configuration.ElasticClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ElasticClientConfiguration.class)
public @interface EnableElasticClient {
}
