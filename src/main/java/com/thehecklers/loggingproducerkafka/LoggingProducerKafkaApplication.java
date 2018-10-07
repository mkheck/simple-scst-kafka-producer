package com.thehecklers.loggingproducerkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableBinding(Source.class)
public class LoggingProducerKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggingProducerKafkaApplication.class, args);
    }
}

@RestController
class MyMessageSource {
    private Source source;

    MyMessageSource(Source source) {
        this.source = source;
    }

    @GetMapping("/hello")
    public String helloMessage() {
        return "Hello!";
    }

    @PostMapping("/send")
    public void sendMessage() {
        System.out.println("Sending message");
        source.output().send(MessageBuilder.withPayload(new Person("SuperDave Osborne")).build());
    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}