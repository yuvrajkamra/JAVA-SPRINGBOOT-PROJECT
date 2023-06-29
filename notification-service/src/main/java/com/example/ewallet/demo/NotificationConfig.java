package com.example.ewallet.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class NotificationConfig {

    @Bean
    ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }

    // producerFactory creates a producer that publishes our messages
    // instance of kafkaproducer factory with customized properties.


    // pass the customized instance of defaultkafka factory to kafka template bean initialization.
    Properties getProperties(){
        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");//kafka server will run on 9092
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return properties;
    }

    // producerFactory creates a Cconsumer that listens our messages
    ConsumerFactory getConsumerFactory(){
        return new DefaultKafkaConsumerFactory(getProperties());
    }

    @Bean
    SimpleMailMessage getMailMessage()
    {
        return new SimpleMailMessage(); // JAVA OBJECT
    }

    @Bean
    JavaMailSender getMailSender()
    {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");//HOST EMAIL ACCOUNT, simple main transfer protocol
        javaMailSender.setPort(587);
        javaMailSender.setUsername("ewalletmymoney@gmail.com");
        javaMailSender.setPassword("Fo12Fo34@");

        Properties properties=javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable",true);// mandatory
        properties.put("mail.debug",true);// Just for debugging purpose

        return  javaMailSender;
    }


}
