package com.ljt.simple_api;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class test {


    private static KafkaProducer<String, String> producer;


    static {
        Properties kfkProperties = new Properties();
        kfkProperties.put("bootstrap.servers", "mini1:9092");
        kfkProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kfkProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(kfkProperties);
    }

    public static void main(String[] args) {
        ProducerRecord<String, String> record = new ProducerRecord<>("first2", "name", "ForgetResult");
        producer.send(record);
        producer.close();
    }
}

