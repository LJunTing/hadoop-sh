package com.ljt.low_api;

import kafka.log.Log;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.logging.Logger;
//import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerConfig;

/**
 * kafka生产者  使用
 */

public class Kafka_prodecer {



    public static void main(String[] args) {
        //配置信息
        Properties properties = new Properties();
        //kafka 集群
        properties.put("bootstrap.servers", "mini1:9092");

        //应答级别   不丢包
        properties.put("acks", "all");
        //重试次数
        properties.put("retries", 0);
        //批量大小 16k
        properties.put("batch.size", 16384);
        //提交延时
        properties.put("linger.ms", 1);
        //缓存 32M
        properties.put("buffer.memory", 33554432);
        //k-v  的序列化类
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.ljt.low_api.CustomerPartitioner");
//        properties.put("","");

        //创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer(properties);
        //循环发送消息
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("first2", String.valueOf(i)), (recordMetadata, e) -> {
                if (e != null) {
                    System.out.println(e);
                }
            });

        }
        producer.close();


    }


}
