package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * エラーモデル
 *
 * エラーレスポンスの詳細を List<String> 型で記述する
 *
 * @property body
 */
data class GenericErrorModelErrors(
    @Schema(required = true, description = "")
    @field:JsonProperty("body", required = true) val body: List<String>,
)
