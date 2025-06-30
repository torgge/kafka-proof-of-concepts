package com.bonespirito.kafka.infraestructure

import com.bonespirito.kafka.infraestructure.properties.KafkaTopicV1Properties
import com.bonespirito.kafka.infraestructure.properties.KafkaTopicV2Properties
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
@EnableConfigurationProperties(KafkaTopicV1Properties::class, KafkaTopicV2Properties::class)
class KafkaConfig(
    val kafkaProperties: KafkaProperties,
) {
    companion object {
        const val SCHEMA_REGISTRY_URL = "schema.registry.url"
        const val BOOTSTRAP_SERVERS = "bootstrap.servers"
        const val KEY_SERIALIZER = "key.serializer"
        const val VALUE_SERIALIZER = "value.serializer"
    }

    @Bean("KafkaStringKeySerializer")
    fun stringKeySerializer(properties: KafkaTopicV1Properties): KafkaTemplate<String, Any> {
        return KafkaTemplate(kafkaKeyStringProducerFactory(properties))
    }

    @Bean("KafkaAvroKeySerializer")
    fun avroKeySerializer(properties: KafkaTopicV2Properties): KafkaTemplate<Any, Any> {
        return KafkaTemplate(kafkaKeyAvroProducerFactory(properties))
    }

    private fun kafkaKeyAvroProducerFactory(properties: KafkaTopicProperties): ProducerFactory<Any, Any> {
        return DefaultKafkaProducerFactory(
            mapOf(
                BOOTSTRAP_SERVERS to kafkaProperties.bootstrapServers,
                SCHEMA_REGISTRY_URL to kafkaProperties.properties[SCHEMA_REGISTRY_URL],
                KEY_SERIALIZER to properties.keySerializer,
                VALUE_SERIALIZER to properties.valueSerializer
            )
        )
    }

    private fun kafkaKeyStringProducerFactory(properties: KafkaTopicProperties): ProducerFactory<String, Any> {
        return DefaultKafkaProducerFactory(
            mapOf(
                BOOTSTRAP_SERVERS to kafkaProperties.bootstrapServers,
                SCHEMA_REGISTRY_URL to kafkaProperties.properties[SCHEMA_REGISTRY_URL],
                KEY_SERIALIZER to properties.keySerializer,
                VALUE_SERIALIZER to properties.valueSerializer,
            )
        )
    }

    @Bean
    fun topicV1(properties: KafkaTopicV1Properties): NewTopic {
        return TopicBuilder.name(properties.name ?: error("TopicV1 name cannot be null"))
            .partitions(properties.partitions)
            .replicas(properties.replicas)
            .build()
    }

    @Bean
    fun topicV2(properties: KafkaTopicV2Properties): NewTopic {
        return TopicBuilder.name(properties.name ?: error("TopicV2 name cannot be null"))
            .partitions(properties.partitions)
            .replicas(properties.replicas)
            .build()
    }
}
