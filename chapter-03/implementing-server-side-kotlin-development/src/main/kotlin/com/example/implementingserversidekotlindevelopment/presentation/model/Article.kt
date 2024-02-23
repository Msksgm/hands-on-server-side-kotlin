package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 単一記事のレスポンスモデル
 *
 * @property slug 記事の slug
 * @property title 記事のタイトル
 * @property description 記事の説明
 * @property body 記事の本文
 */
data class Article(
    @Schema(example = "283e60096c26aa3a39cf04712cdd1ff7", required = true, description = "記事の slug")
    @field:JsonProperty("slug", required = true) val slug: String,

    @Schema(example = "article-title", required = true, description = "記事のタイトル")
    @field:JsonProperty("title", required = true) val title: String,

    @Schema(example = "article-description", required = true, description = "記事の説明")
    @field:JsonProperty("description", required = true) val description: String,

    @Schema(example = "article-body", required = true, description = "記事の本文")
    @field:JsonProperty("body", required = true) val body: String,
)
