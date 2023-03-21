package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.ValidatedNel
import arrow.core.invalidNel
import arrow.core.validNel
import com.example.implementingserversidekotlindevelopment.util.ValidationError

/**
 * 作成済記事の本文の値オブジェクト
 *
 */
interface Body {
    /**
     * 本文
     */
    val value: String

    /**
     * Validation 有り、作成済記事の本文
     *
     * @property value
     */
    private data class ValidatedBody(override val value: String) : Body

    /**
     * Validation 無し、作成済記事の本文
     *
     * @property value
     */
    private data class BodyWithoutValidation(override val value: String) : Body

    /**
     * Factory メソッド
     *
     * @constructor Create empty Companion
     */
    companion object {
        private const val maximumLength: Int = 1024

        /**
         * Validation 無し
         *
         * @param body
         * @return
         */
        fun newWithoutValidation(body: String): Body = BodyWithoutValidation(body)

        /**
         * Validation 有り
         *
         * @param body
         * @return
         */
        fun new(body: String?): ValidatedNel<CreationError, Body> {
            /**
             * null チェック
             *
             * 空白だった場合、早期リターン
             */
            if (body == null) {
                return CreationError.Required.invalidNel()
            }

            /**
             * 文字数チェック
             *
             * 最大文字数より長かったら、早期リターン
             */
            if (body.length > maximumLength) {
                return CreationError.TooLong(maximumLength).invalidNel()
            }

            return ValidatedBody(body).validNel()
        }
    }

    /**
     * オブジェクト生成時のドメインルール
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
                get() = "body は必須です"
        }

        /**
         * 文字数制限
         *
         * 長すぎては駄目
         *
         * @property maximum
         */
        data class TooLong(val maximum: Int) : CreationError {
            override val message: String
                get() = "body は $maximum 文字以下にしてください"
        }
    }
}
