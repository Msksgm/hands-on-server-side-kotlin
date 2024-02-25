package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

/**
 * エラーモデル
 *
 * エラーの内容レスポンスモデル
 *
 * @property errors
 */
data class GenericErrorModel(
    @field:Valid
    @Schema(required = true, description = "")
    @field:JsonProperty("errors", required = true) val errors: GenericErrorModelErrors
)
