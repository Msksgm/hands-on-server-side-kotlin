package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

/**
 * 新規作成記事
 *
 * @param title 新規作成記事のタイトル
 * @param description 新規作成記事の説明
 * @param body 新規作成記事の本文
 */
data class NewArticle(
    @Schema(example = "new-article-title", required = true, description = "新規作成記事のタイトル")
    @field:Valid
    @field:NotBlank
    @field:Length(min = 0, max = 32)
    @field:JsonProperty("title", required = true) private val title: String? = null,

    @Schema(example = "new-article-description", required = true, description = "新規作成記事の説明")
    @field:Valid
    @field:NotNull
    @field:Length(min = 0, max = 64)
    @field:JsonProperty("description", required = true) private val description: String? = null,

    @Schema(example = "new-article-body", required = true, description = "新規作成記事の本文")
    @field:Valid
    @field:NotNull
    @field:Length(min = 0, max = 1024)
    @field:JsonProperty("body", required = true) private val body: String? = null,
) {
    /**
     * 新規作成記事のタイトル
     *
     * @NotBlank アノテーションを付与しているため、null 非許容型に変換
     *
     * @return
     */
    @get:Schema(hidden = true)
    val validatedTitle: String
        get() = title!!

    /**
     * 新規作成記事の説明
     *
     * @NotNull アノテーションを付与しているため、null 非許容型に変換
     *
     * @return
     */
    @get:Schema(hidden = true)
    val validatedDescription: String
        get() = description!!

    /**
     * 新規作成記事の本文
     *
     * @NotNull アノテーションを付与しているため、null 非許容型に変換
     *
     * @return
     */
    @get:Schema(hidden = true)
    val validatedBody: String
        get() = body!!
}
