package com.bonespirito.kafka.infraestructure

interface KafkaTopicProperties {
    val name: String?
    val partitions: Int?
    val replicas: Int?
    val keySerializer: String?
    val valueSerializer: String?
}
