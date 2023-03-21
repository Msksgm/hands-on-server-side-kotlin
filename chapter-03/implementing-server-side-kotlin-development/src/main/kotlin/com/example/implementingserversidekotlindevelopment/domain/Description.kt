package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.ValidatedNel
import arrow.core.invalidNel
import arrow.core.validNel
import com.example.implementingserversidekotlindevelopment.util.ValidationError

/**
 * 「作成済記事の概要」の値オブジェクト
 *
 */
interface Description {
    /**
     * 概要
     */
    val value: String

    /**
     * Validation 有り、作成済記事の概要
     *
     * @property value
     */
    private data class ValidatedDescription(override val value: String) : Description

    /**
     * Validation 無し、作成済記事の概要
     *
     * @property value
     */
    private data class DescriptionWithoutValidation(override val value: String) : Description

    /**
     * Factory メソッド
     *
     * @constructor Create empty Companion
     */
    companion object {
        private const val maximumLength: Int = 64

        /**
         * Validation 有り
         *
         * @param description
         * @return
         */
        fun new(description: String?): ValidatedNel<CreationError, Description> {
            /**
             * null チェック
             *
             * 空白だった場合、早期リターン
             */
            if (description == null) {
                return CreationError.Required.invalidNel()
            }

            /**
             * 文字数チェック
             *
             * 最大文字数より長かったら、早期リターン
             */
            if (description.length > maximumLength) {
                return CreationError.TooLong(maximumLength).invalidNel()
            }

            return ValidatedDescription(description).validNel()
        }

        /**
         * Validation 無し
         *
         * @param description
         * @return
         */
        fun newWithoutValidation(description: String): Description = DescriptionWithoutValidation(description)
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
                get() = "description を入力してください"
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
                get() = "description は $maximum 文字以下にしてください"
        }
    }
}
