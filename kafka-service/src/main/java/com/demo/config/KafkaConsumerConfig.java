package com.demo.config;


import com.demo.dto.HttpServiceDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
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

    @Value("${spring.kafka.username}")
    private String username;

    @Value("${spring.kafka.password}")
    private String password;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

        private Map<String, Object> baseConsumerConfig() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "apartcom");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
            props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
            props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.demo.dto");
            props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, HttpServiceDto.class.getName());
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
            return props;
        }

        private void configureSecurity(Map<String, Object> props) {
            if (username != null && !username.isBlank()) {
                props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
                props.put("security.protocol", "SASL_PLAINTEXT");
                props.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                        "%s required username=\"%s\" password=\"%s\";",
                        PlainLoginModule.class.getName(),
                        username,
                        password
                ));
            }
        }

        @Bean
        public ConsumerFactory<String, Object> consumerFactory() {
            Map<String, Object> props = baseConsumerConfig();
            configureSecurity(props);
            return new DefaultKafkaConsumerFactory<>(props);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            factory.getContainerProperties().setMissingTopicsFatal(false);
            factory.setConcurrency(4);
            return factory;
        }
}
