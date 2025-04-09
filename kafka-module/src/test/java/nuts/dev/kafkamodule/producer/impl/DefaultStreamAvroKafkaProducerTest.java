package nuts.dev.kafkamodule.producer.impl;

import model.StreamAvroModel;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DefaultStreamAvroKafkaProducerTest {
    @Mock
    private KafkaTemplate<Long, StreamAvroModel> kafkaTemplate;

    @InjectMocks
    private DefaultStreamAvroKafkaProducer producer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSend_Success() {
        // given
        String topicName = "test-topic";
        Long key = 1L;
        StreamAvroModel message = new StreamAvroModel(); // 가정: StreamAvroModel 클래스가 기본 생성자를 제공한다.

        // 가짜 SendResult 생성
        SendResult<Long, StreamAvroModel> sendResult = mock(SendResult.class);
        RecordMetadata recordMetadata = mock(RecordMetadata.class);
        when(recordMetadata.topic()).thenReturn(topicName);
        when(recordMetadata.partition()).thenReturn(1);
        when(recordMetadata.offset()).thenReturn(100L);
        when(recordMetadata.timestamp()).thenReturn(System.currentTimeMillis());
        when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);

        CompletableFuture<SendResult<Long, StreamAvroModel>> future = CompletableFuture.completedFuture(sendResult);
        when(kafkaTemplate.send(eq(topicName), eq(key), eq(message))).thenReturn(future);

        // when
        producer.send(topicName, key, message);

        // then
        verify(kafkaTemplate, times(1)).send(eq(topicName), eq(key), eq(message));
    }




}