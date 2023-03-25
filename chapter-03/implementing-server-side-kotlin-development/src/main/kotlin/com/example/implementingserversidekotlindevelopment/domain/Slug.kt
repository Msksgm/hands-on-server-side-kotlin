package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.ValidatedNel
import arrow.core.invalidNel
import arrow.core.validNel
import com.example.implementingserversidekotlindevelopment.util.ValidationError
import java.util.*

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
         * Validation なし
         *
         * @param slug
         * @return
         */
        fun newWithoutValidation(slug: String): Slug = SlugWithoutValidation(slug)

        /**
         * Validation あり
         *
         * @param slug
         * @return
         */
        fun new(slug: String?): ValidatedNel<CreationError, Slug> {
            /**
             * Null チェック
             *
             * 空白だった場合、早期リターン
             */
            if (slug == null) {
                return CreationError.Required.invalidNel()
            }

            /**
             * format チェック
             *
             * format が適切でなかったら、早期リターン
             */
            if (!slug.matches(Regex(format))) {
                return CreationError.ValidFormat(slug).invalidNel()
            }
            return ValidatedSlug(slug).validNel()
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
         * 必須
         *
         * Null は許容しない
         *
         * @constructor Create empty Required
         */
        object Required : CreationError {
            override val message: String
                get() = "slug は必須です"
        }

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
