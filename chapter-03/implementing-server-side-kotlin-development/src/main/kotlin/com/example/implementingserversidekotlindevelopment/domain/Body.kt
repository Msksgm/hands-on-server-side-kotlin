package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.EitherNel
import arrow.core.leftNel
import arrow.core.right
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
        fun new(body: String): EitherNel<CreationError, Body> {
            /**
             * 文字数チェック
             *
             * 最大文字数より長かったら、早期リターン
             */
            if (body.length > maximumLength) {
                return CreationError.TooLong(maximumLength).leftNel()
            }

            return ValidatedBody(body).right()
        }
    }

    /**
     * オブジェクト生成時のドメインルール
     *
     */
    sealed interface CreationError : ValidationError {
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
