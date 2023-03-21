package arrow_error_handling_01

import arrow.core.Either
import arrow.core.left
import arrow.core.right

/**
 * main 関数
 * 2 つの整数の標準入力を受け取る
 *
 * @param args
 */
fun main(args: Array<String>) {
    val a: Int = readLine()?.toInt()!! // 標準入力
    val b: Int = readLine()?.toInt()!! // 標準入力

    val result = divide(a, b) // 戻り値は Either<String, Int>
    when (result) { // when 式による型比較
        is Either.Left -> { // 失敗時
            println("失敗原因: ${result.value}")
        }

        is Either.Right -> { // 成功時
            println("除算結果: ${result.value}")
        }
    }
}

/**
 * 除算をする関数
 *
 * @param a 被除数
 * @param b 除数
 * @return 失敗:失敗理由（文字列）、成功:除算の結果（整数）
 */
fun divide(a: Int, b: Int): Either<String, Int> {
    return when (b == 0) {
        true -> "除数を 0 にしてはいけません".left() // 失敗ケース
        false -> (a / b).right() // 成功ケース
    }
}
