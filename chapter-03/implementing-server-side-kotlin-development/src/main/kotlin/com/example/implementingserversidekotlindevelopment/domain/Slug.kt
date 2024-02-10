package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.EitherNel
import arrow.core.leftNel
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.util.ValidationError
import java.util.UUID

/**
 * 作成済記事の Slug
 *
 */
interface Slug {
    /**
     * slug の値
     */
    val value: String

    /**
     * new から生成された slug
     *
     * @property value
     */
    private data class ValidatedSlug(override val value: String) : Slug

    /**
     * new 以外から生成された slug
     *
     * @property value
     */
    private data class SlugWithoutValidation(override val value: String) : Slug

    companion object {
        private const val format: String = "^[a-z0-9]{32}$"

        /**
         * Validation 無し
         *
         * @param slug
         * @return
         */
        fun newWithoutValidation(slug: String): Slug = SlugWithoutValidation(slug)

        /**
         * Validation 有り
         *
         * @param slug
         * @return
         */
        fun new(slug: String): EitherNel<CreationError, Slug> {
            /**
             * format チェック
             *
             * format が適切でなかったら、早期リターン
             */
            if (!slug.matches(Regex(format))) {
                return CreationError.ValidFormat(slug).leftNel()
            }

            return ValidatedSlug(slug).right()
        }

        /**
         * 引数有りの場合、UUID から生成
         *
         * @return
         */
        fun new(): Slug {
            return ValidatedSlug(UUID.randomUUID().toString().split("-").joinToString(""))
        }
    }

    /**
     * Slug 生成時のドメインルール
     *
     */
    sealed interface CreationError : ValidationError {
        /**
         * フォーマット確認
         *
         * 指定された format 以外は駄目
         *
         * @property slug
         */
        data class ValidFormat(val slug: String) : CreationError {
            override val message: String
                get() = "slug は 32 文字の英小文字数字です。"
        }
    }
}
