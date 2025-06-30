package com.bonespirito.kafka.infraestructure.properties

import com.bonespirito.kafka.infraestructure.KafkaTopicProperties
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kafka.topics.topic-v1")
class KafkaTopicV1Properties : KafkaTopicProperties {
    override var name: String? = null
    override var partitions: Int = 1
    override var replicas: Int = 1
    override var keySerializer: String? = null
    override var valueSerializer: String? = null
}
