package com.bonespirito.kafka.producer

import com.bonespirito.kafka.avro.StockKey
import com.bonespirito.kafka.avro.StockValue
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class StockProducer(
    private val kafkaTemplate: KafkaTemplate<Any, Any>
) {

    fun sendToV1(stockValue: StockValue) {
        kafkaTemplate.send("com.bonespirito.stock.v1", stockValue.getSkuCode(), stockValue)
    }

    fun sendToV2(stockKey: StockKey, stockValue: StockValue) {
        kafkaTemplate.send("com.bonespirito.stock.v2", stockKey, stockValue)
    }
}
