package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.EitherNel
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.example.implementingserversidekotlindevelopment.util.ValidationError

/**
 * 更新用の作成済記事インタフェース
 *
 */
interface UpdatableCreatedArticle {
    /**
     * Title 更新用の作成済記事のタイトル
     */
    val title: Title

    /**
     * Description 更新用の作成済記事の概要
     */
    val description: Description

    /**
     * Body 更新用の作成済記事の本文
     */
    val body: Body

    /**
     * バリデーション済の更新用作成済記事
     *
     * @property title
     * @property description
     * @property body
     */
    private data class ValidatedUpdatableCreatedArticle(
        override val title: Title,
        override val description: Description,
        override val body: Body,
    ) : UpdatableCreatedArticle

    companion object {
        /**
         * 作成済記事の更新
         *
         * @param title
         * @param description
         * @param body
         * @return
         */
        fun new(
            title: String,
            description: String,
            body: String,
        ): EitherNel<ValidationError, UpdatableCreatedArticle> {
            return either {
                zipOrAccumulate(
                    { Title.new(title).bindNel() },
                    { Description.new(description).bindNel() },
                    { Body.new(body).bindNel() },
                ) { validatedTitle, validatedDescription, validatedBody ->
                    ValidatedUpdatableCreatedArticle(
                        validatedTitle,
                        validatedDescription,
                        validatedBody
                    )
                }
            }
        }
    }
}
