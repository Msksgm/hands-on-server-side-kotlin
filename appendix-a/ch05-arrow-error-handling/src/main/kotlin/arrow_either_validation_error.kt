package arrow_either_validation_error

import arrow.core.Either
import arrow.core.left
import arrow.core.right

fun main() {
    /**
     * 名前も年齢も不正な入力をしたパターン
     * 名前のバリデーションエラーしか返されない
     */
    when (val person01 = createPerson("    ", -1)) {
        is Either.Left -> {
            println("person01 の生成に失敗しました。${person01.value.message}")
        }

        is Either.Right -> {
            println("person01 の生成に成功しました。")
            println(person01.value)
        }
    }

    /**
     *  名前は正しい入力をしたが、年齢が不正なパターン
     *  名前のバリデーションを通過して、年齢のバリデーションがされる
     */
    when (val person02 = createPerson("Alice", -1)) {
        is Either.Left -> {
            println("person02 の生成に失敗しました。${person02.value.message}")
        }

        is Either.Right -> {
            println("person02 の生成に成功しました")
            println(person02.value)
        }
    }

    /**
     * 名前も年齢も正しいパターン
     */
    when (val person03 = createPerson("Alice", 23)) {
        is Either.Left -> {
            println("person03 の生成に失敗しました。${person03.value.message}")
        }

        is Either.Right -> {
            println("person03 の生成に成功しました")
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
fun createPerson(name: String, age: Int): Either<ValidationError, Person> {
    val validatedName = when (val it = Name.create(name)) {
        is Either.Left -> {
            return it
        }

        is Either.Right -> {
            it.value
        }
    }

    val validatedAge = when (val it = Age.create(age)) {
        is Either.Left -> {
            return it
        }

        is Either.Right -> {
            it.value
        }
    }

    return Person(validatedName, validatedAge).right()
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
        fun create(name: String): Either<ValidationError, Name> {
            /**
             * 未記入または空白のときバリデーションエラーで早期 return
             */
            if (name.isEmpty() || name.isBlank()) {
                return ValidationError("名前を入力してください").left()
            }
            return Name(name).right()
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
        fun create(age: Int): Either<ValidationError, Age> {
            /**
             * サービスの対象外の年齢を入力されたときバリデーションエラー
             */
            if (age < 0 || age > 150) {
                return ValidationError("年齢は 0 歳以上 150 歳未満で入力してください").left()
            }
            return Age(age).right()
        }
    }
}
