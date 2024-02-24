package com.example.implementingserversidekotlindevelopment.presentation.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

/**
 * 更新記事
 *
 * @property title 作成済記事の更新後タイトル
 * @property description 新規作成記事の更新後説明
 * @property body 新規作成記事の更新後本文
 */
data class UpdateArticle(
    @Schema(example = "null", description = "")
    @field:Valid
    @field:NotBlank
    @field:Length(min = 0, max = 32)
    @field:JsonProperty("title") private val title: String? = null,

    @Schema(example = "null", description = "")
    @field:Valid
    @field:Length(min = 0, max = 64)
    @field:NotNull
    @field:JsonProperty("description") private val description: String? = null,

    @Schema(example = "null", description = "")
    @field:Valid
    @field:Length(min = 0, max = 1024)
    @field:NotNull
    @field:JsonProperty("body") private val body: String? = null
) {
    /**
     * 作成済記事の更新後タイトル
     *
     * @NotBlank アノテーションを付与しているため、null 非許容型に変換
     *
     * @return
     */
    @get:Schema(hidden = true)
    val validatedTitle: String
        get() = title!!

    /**
     * 新規作成記事の更新後説明
     *
     * @NotNull アノテーションを付与しているため、null 非許容型に変換
     *
     * @return
     */
    @get:Schema(hidden = true)
    val validatedDescription: String
        get() = description!!

    /**
     * 新規作成記事の更新後本文
     *
     * @NotNull アノテーションを付与しているため、null 非許容型に変換
     *
     * @return
     */
    @get:Schema(hidden = true)
    val validatedBody: String
        get() = body!!
}
