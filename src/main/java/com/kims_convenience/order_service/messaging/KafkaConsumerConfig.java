package com.kims_convenience.order_service.messaging;

import com.kims_convenience.order_service.dto.order_event.OrderSubmittedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, OrderSubmittedEvent> orderSubmittedEventConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");  // Skip old messages

        // Use ErrorHandlingDeserializer for both key and value
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        // Convey ErrorHandlingDeserializer what the real deserializers are
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        // Configure JsonDeserializer for value (OrderSubmittedEvent)
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderSubmittedEvent.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderSubmittedEvent> orderSubmittedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderSubmittedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderSubmittedEventConsumerFactory());
        return factory;
    }
}
