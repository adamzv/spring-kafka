package com.example.springkafka;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.function.Function;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Bean
    NewTopic aircraft2() {
        return TopicBuilder.name("aircraft2").partitions(1).replicas(3).build();
    }

    @Bean
    NewTopic esports() {
        return TopicBuilder.name("esports").partitions(1).replicas(3).build();
    }

}

@RequiredArgsConstructor
@Component
@Slf4j
class Producer {

    private final KafkaTemplate<Integer, String> template;

    Faker faker;

    // @EventListener(ApplicationStartedEvent.class)
    public void generate() {
        faker = Faker.instance();
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

        Flux<String> aircraft = Flux.fromStream(Stream.generate(() -> faker.aviation().aircraft()));

        Flux.zip(interval, aircraft)
                .log()
                .map(objects -> template.send("aircraft2", faker.random().nextInt(42), objects.getT2())).blockLast();
    }
}

@Component
@RequiredArgsConstructor
@Slf4j
class Consumer {

    // @KafkaListener(topics = {"aircraft"}, groupId = "spring-boot-kafka")
    public void consume(ConsumerRecord<Integer, String> record) {
        log.info("received = [{}, {}]", record.key(), record.value());
    }
}