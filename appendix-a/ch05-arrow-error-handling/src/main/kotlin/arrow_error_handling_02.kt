package arrow_error_handling_02

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlin.math.abs
import kotlin.random.Random

/**
 * main 関数
 * API 呼び出しをおこない、成功か失敗かで分岐する疑似コード
 *
 * @param args
 */
fun main() {
    val result = apiCall()
    when (result) {
        is Either.Left -> when (val it = result.value) {
            is ApiCallError.NotFound -> println(it.message)
            is ApiCallError.ServerError -> println("予期しないエラーが発生しました")
        }

        is Either.Right -> {
            println(result.value.message)
        }
    }
}

/**
 * API 呼び出し
 *
 * 1/3 の確率で成功し、2/3 の確率で失敗する疑似コード
 *
 * @return 失敗: ApiCallError、成功: ApiCallSuccess
 */
fun apiCall(): Either<ApiCallError, ApiCallSuccess> {
    return when (abs(Random.nextInt() % 3)) { // 1、2、3 のいずれか
        0 -> ApiCallSuccess(message = "成功しました").right()
        1 -> ApiCallError.NotFound(message = "見つかりませんでした").left()
        else -> ApiCallError.ServerError.left()
    }
}

/**
 * apiCall が成功したときの戻り値
 *
 * @property message
 */
data class ApiCallSuccess(val message: String)

/**
 * apiCall が失敗したときの戻り値
 *
 */
sealed interface ApiCallError {
    /**
     * 何も見つからなかったとき
     *
     * @property message
     */
    data class NotFound(val message: String) : ApiCallError

    /**
     * サーバーでエラーが発生したとき
     *
     * @constructor Create empty Server error
     */
    object ServerError : ApiCallError
}
