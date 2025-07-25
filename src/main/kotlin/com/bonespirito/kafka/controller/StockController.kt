package com.bonespirito.kafka.controller

import com.bonespirito.kafka.producer.StockProducer
import com.bonespirito.supply.stock.StockKeyV2
import com.bonespirito.supply.stock.StockValueV1
import com.bonespirito.supply.stock.StockValueV2
import com.bonespirito.supply.stock.StorageLocationType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stock")
class StockController(private val stockProducer: StockProducer) {

    @PostMapping("/v1")
    fun sendToV1() {
        val stockValue = StockValueV1.newBuilder()
            .setSkuCode("SKU-" + (10000..99999).random())
            .setDistributionCenter("DC-" + (100..999).random())
            .setQuantity((1L..1000L).random())
            .build()
        stockProducer.sendToV1(stockValue)
    }

    @PostMapping("/v2")
    fun sendToV2() {

        val sku = "SKU-" + (10000..99999).random()
        val dc = "DC-" + (100..999).random()
        val sl = "SL-" + (1000..9999).random()
        val slType = listOf(StorageLocationType.INTERNAL, StorageLocationType.EXTERNAL).random()

        val stockKey = StockKeyV2.newBuilder()
            .setSkuCode(sku)
            .setDistributionCenter(dc)
            .setStorageLocation(sl)
            .setStorageLocationType(slType)
            .build()
        val stockValue = StockValueV2.newBuilder()
            .setSkuCode(sku)
            .setDistributionCenter(dc)
            .setStorageLocation(sl)
            .setQuantity((1L..1000L).random())
            .setStorageLocationType(slType)
            .build()
        stockProducer.sendToV2(stockKey, stockValue)
    }
}
