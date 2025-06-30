package com.bonespirito.kafka.producer

import com.bonespirito.kafka.infraestructure.properties.KafkaTopicV1Properties
import com.bonespirito.kafka.infraestructure.properties.KafkaTopicV2Properties
import com.bonespirito.supply.stock.StockKeyV2
import com.bonespirito.supply.stock.StockValueV1
import com.bonespirito.supply.stock.StockValueV2
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class StockProducer(
    @Qualifier("KafkaAvroKeySerializer")
    private val kafkaAvroKeyTemplate: KafkaTemplate<Any, Any>,
    @Qualifier("KafkaStringKeySerializer")
    private val kafkaStringKeyTemplate: KafkaTemplate<String, Any>,
    val kafkaTopicV1Properties: KafkaTopicV1Properties,
    val kafkaTopicV2Properties: KafkaTopicV2Properties,
) {

    fun sendToV1(stockValue: StockValueV1) {
        kafkaStringKeyTemplate.send(kafkaTopicV1Properties.name!!,
            "${stockValue.skuCode}${stockValue.distributionCenter}", stockValue)
    }

    fun sendToV2(stockKey: StockKeyV2, stockValue: StockValueV2) {
        kafkaAvroKeyTemplate.send(kafkaTopicV2Properties.name!!, stockKey, stockValue)
    }
}
