package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

/**
 * 複数記事のレスポンスモデル
 *
 * @param articles
 * @param articleCount
 */
data class MultipleArticleResponse(
    @field:Valid
    @Schema(required = true, description = "")
    @field:JsonProperty("articles", required = true) val articles: List<Article>,

    @Schema(example = "3", description = "")
    @field:JsonProperty("articleCount") val articleCount: Int,
)
