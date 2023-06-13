package com.example.springkafka;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestKafkaController {

    private final KafkaTemplate<Integer, String> template;

    @GetMapping("/test")
    public String test() {
        var aircraft = new Faker().aviation().aircraft();
        var message = template.send("aircraft", aircraft);
        message.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message was successfully sent: {} - {}", result.getProducerRecord().value(), result.getRecordMetadata().timestamp());
            } else {
                log.error("Error sending message to Kafka: {}", ex.getMessage());
            }
        });
        return aircraft;
    }

    @GetMapping("/esports")
    public String esports() {
        var esports = new Faker().esports().team();
        var message = template.send("esports", esports);
        message.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message was successfully sent: {} - {}", result.getProducerRecord().value(), result.getRecordMetadata().timestamp());
            } else {
                log.error("Error sending message to Kafka: {}", ex.getMessage());
            }
        });
        return esports;
    }
}
