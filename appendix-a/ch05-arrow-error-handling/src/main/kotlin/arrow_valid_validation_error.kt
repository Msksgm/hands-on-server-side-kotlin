package arrow_valid_validation_error

import arrow.core.Validated
import arrow.core.ValidatedNel
import arrow.core.invalidNel
import arrow.core.valid
import arrow.core.zip

fun main() {
    /**
     * 名前も年齢も不正な入力をしたパターン
     * 両方のバリデーションが失敗した結果が返される
     */
    when (val person01 = createPerson("    ", -1)) {
        is Validated.Invalid -> {
            println("person01 の生成に失敗しました。${person01.value.all.joinToString(separator = ", ") { it.message }}")
        }

        is Validated.Valid -> {
            println("person01 の生成に成功しました。")
            println(person01.value)
        }
    }

    /**
     *  名前は正しい入力をしたが、年齢が不正なパターン
     *  名前のバリデーションが通過して、年齢のバリデーションで失敗したことがわかる
     */
    when (val person02 = createPerson("Alice", -1)) {
        is Validated.Invalid -> {
            println("person02 の生成に失敗しました。${person02.value.all.joinToString(separator = ", ") { it.message }}")
        }

        is Validated.Valid -> {
            println("person02 の生成に成功しました。")
            println(person02.value)
        }
    }

    /**
     * 名前も年齢も正しいパターン
     */
    when (val person03 = createPerson("Alice", 23)) {
        is Validated.Invalid -> {
            println("person03 の生成に失敗しました。${person03.value.all.joinToString(separator = ", ") { it.message }}")
        }

        is Validated.Valid -> {
            println("person03 の生成に成功しました。")
            println(person03.value)
        }
    }
}

/**
 * 人物オブジェクト
 *
 * @property name
 * @property age
 */
data class Person(
    val name: Name,
    val age: Age,
)

/**
 * 人物オブジェクトのファクトリ関数
 *
 * @param name
 * @param age
 * @return
 */
fun createPerson(name: String, age: Int): ValidatedNel<ValidationError, Person> {
    /**
     * zip は ValidatedNel が戻り値の関数に対してまとめて検証できる
     *
     * 下記の例では、Name と Age のバリデーションをまとめて実施して、失敗だった場合 Nel<ValidationError> 型、成功だった場合 Person 型を返す
     */
    return Name.create(name).zip(
        Age.create(age)
    ) { validatedName, validatedAge -> Person(validatedName, validatedAge) }
}

data class ValidationError(val message: String)

/**
 * 名前オブジェクト
 *
 * @property value
 */
class Name private constructor(val value: String) {
    companion object {
        /**
         * 名前オブジェクトのファクトリ
         *
         * @param name
         * @return 失敗:バリデーションエラー、成功:名前オブジェクト
         */
        fun create(name: String): ValidatedNel<ValidationError, Name> {
            /**
             * 未記入または空白のときバリデーションエラーで早期 return
             */
            if (name.isEmpty() || name.isBlank()) {
                return ValidationError("名前を入力してください").invalidNel()
            }
            return Name(name).valid()
        }
    }
}

/**
 * 年齢オブジェクト
 *
 * @property value
 */
class Age private constructor(val value: Int) {
    companion object {
        /**
         * 年齢オブジェクトのファクトリ
         *
         * @param age 年齢
         * @return 失敗:バリデーションエラー、成功:年齢オブジェクト
         */
        fun create(age: Int): ValidatedNel<ValidationError, Age> {
            /**
             * サービスの対象外の年齢を入力されたときバリデーションエラー
             */
            if (age < 0 || age > 150) {
                return ValidationError("年齢は 0 歳以上 150 歳未満で入力してください").invalidNel()
            }
            return Age(age).valid()
        }
    }
}
