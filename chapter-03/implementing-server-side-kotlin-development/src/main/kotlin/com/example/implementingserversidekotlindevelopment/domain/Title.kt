package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.EitherNel
import arrow.core.leftNel
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.util.ValidationError

/**
 * 作成済記事のタイトルの値オブジェクト
 */
interface Title {
    /**
     * タイトル名
     */
    val value: String

    /**
     * Validation 有り、作成済記事のタイトル
     *
     * @property value
     */
    private data class ValidatedTitle(override val value: String) : Title

    /**
     * Validation 無し、作成済記事のタイトル
     *
     * @property value
     */
    private data class TitleWithoutValidation(override val value: String) : Title

    /**
     * Factory メソッド
     *
     * @constructor Create empty Companion
     */
    companion object {
        private const val maximumLength = 32

        /**
         * Validation 無し
         *
         * @param title
         * @return
         */
        fun newWithoutValidation(title: String): Title = TitleWithoutValidation(title)

        /**
         * Validation 有り
         *
         * @param title
         * @return
         */
        fun new(title: String): EitherNel<CreationError, Title> {
            /**
             * null、空白チェック
             *
             * 空白だった場合、早期リターン
             */
            if (title.isBlank()) {
                return CreationError.Required.leftNel()
            }

            /**
             * 文字数確認
             *
             * 最大文字数より長かったら、早期リターン
             */
            if (title.length > maximumLength) {
                return CreationError.TooLong(maximumLength).leftNel()
            }

            return ValidatedTitle(title).right()
        }
    }

    /**
     * タイトル生成時のドメインルール
     *
     */
    sealed interface CreationError : ValidationError {
        /**
         * 必須
         *
         * 空文字列や空白を許容しない
         *
         * @constructor Create empty Required
         */
        object Required : CreationError {
            override val message: String
                get() = "title は必須です"
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
                get() = "title は $maximum 文字以下にしてください。"
        }
    }
}
