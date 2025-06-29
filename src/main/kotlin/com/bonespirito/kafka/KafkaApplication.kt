package com.bonespirito.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import org.apache.kafka.clients.admin.NewTopic

@SpringBootApplication
class KafkaApplication {

	@Bean
	fun topicV1(): NewTopic {
		return TopicBuilder.name("com.bonespirito.stock.v1")
			.partitions(1)
			.replicas(1)
			.build()
	}

	@Bean
	fun topicV2(): NewTopic {
		return TopicBuilder.name("com.bonespirito.stock.v2")
			.partitions(1)
			.replicas(1)
			.build()
	}
}

fun main(args: Array<String>) {
	runApplication<KafkaApplication>(*args)
}
