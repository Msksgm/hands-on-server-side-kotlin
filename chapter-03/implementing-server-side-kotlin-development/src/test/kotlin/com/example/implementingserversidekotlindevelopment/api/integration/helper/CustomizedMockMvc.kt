package com.example.implementingserversidekotlindevelopment.api.integration.helper

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder

/**
 * MockMvc のカスタマイズ
 *
 * Response の Content-Type に"charset=UTF-8"を付与
 * 理由
 * - デフォルトだと日本語が文字化けするため
 */
@Component
class CustomizedMockMvc : MockMvcBuilderCustomizer {
    override fun customize(builder: ConfigurableMockMvcBuilder<*>?) {
        builder?.alwaysDo { result -> result.response.characterEncoding = "UTF-8" }
    }
}
