package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

/**
 * 新規作成のリクエストモデル
 *
 * @param article
 */
data class NewArticleRequest(
    @field:Valid
    @Schema(required = true, description = "")
    @field:NotNull
    @field:JsonProperty("article", required = true) private val article: NewArticle? = null,
) {
    /**
     * 新規作成記事のプロパティ
     *
     * @NotNull アノテーションを付与しているため、null 非許容型に変換
     */
    @get:Schema(hidden = true)
    val validatedArticle: NewArticle
        get() = article!!
}
