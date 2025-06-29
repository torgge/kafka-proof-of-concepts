package com.bonespirito.kafka.controller

import com.bonespirito.kafka.avro.StockKey
import com.bonespirito.kafka.avro.StorageLocationType
import com.bonespirito.kafka.avro.StockValue
import com.bonespirito.kafka.producer.StockProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stock")
class StockController(private val stockProducer: StockProducer) {

    @PostMapping("/v1")
    fun sendToV1() {
        val stockValue = StockValue.newBuilder()
            .setSkuCode("SKU-123")
            .setDistributionCenter("DC-456")
            .setStorageLocation("SL-789")
            .setQuantity(100L)
            .setStorageLocationType(StorageLocationType.INTERNAL)
            .build()
        stockProducer.sendToV1(stockValue)
    }

    @PostMapping("/v2")
    fun sendToV2() {
        val stockKey = StockKey.newBuilder()
            .setSkuCode("SKU-123")
            .setDistributionCenter("DC-456")
            .setStorageLocation("SL-789")
            .setStorageLocationType(StorageLocationType.INTERNAL)
            .build()
        val stockValue = StockValue.newBuilder()
            .setSkuCode("SKU-123")
            .setDistributionCenter("DC-456")
            .setStorageLocation("SL-789")
            .setQuantity(200L)
            .setStorageLocationType(StorageLocationType.EXTERNAL)
            .build()
        stockProducer.sendToV2(stockKey, stockValue)
    }
}
