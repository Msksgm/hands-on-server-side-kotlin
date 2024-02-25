package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

/**
 * 単一記事取得のレスポンス
 *
 * @property article 作成済み記事
 */
data class SingleArticleResponse(
    @field:Valid
    @Schema(required = true, description = "")
    @field:JsonProperty("article", required = true) val article: Article
)
