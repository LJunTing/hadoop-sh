package com.ljt.low_api;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class CustomerConumer {
    public static void main(String[] args) {

        Properties props = new Properties();
        //kafka 集群
        props.put("bootstrap.servers", "mini1:9092");
        //消费者组  id
        props.put("group.id", "test");
        //是否自动提交offeset
        props.put("enable.auto.commit", "true");
        //自动提交的延迟
        props.put("auto.commit.interval.ms", "1000");
        //反序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //创建消费者对像
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //指定topic
        consumer.subscribe(Arrays.asList("first2"));

        //获取数据
        while (true) {
            //Duration 将时间长度封装
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(30));
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }

    }

}
