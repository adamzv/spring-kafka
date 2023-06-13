package com.example.springkafka;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

// NOT NECESSARY since spring can setup kafka template automatically
@Configuration
public class KafkaConfiguration {

//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(
//                Map.of(BOOTSTRAP_SERVERS_CONFIG, "localhost:29094",
//                        RETRIES_CONFIG, 0,
//                        BUFFER_MEMORY_CONFIG, 33554432,
//                        KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
//                        VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
//                ));
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}
