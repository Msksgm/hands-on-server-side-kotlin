package try_catch_error_handling

/**
 * main 関数
 * 2 つの整数の標準入力を受け取る
 *
 * @param args
 */
fun main(args: Array<String>) {
    val a: Int = readLine()?.toInt()!! // 標準入力
    val b: Int = readLine()?.toInt()!! // 標準入力

    try {
        val result = divide(a, b)
        println("除算結果: ${result}")
    } catch (e: Exception) {
        println("失敗原因: ${e.message}")
    }
}

/**
 * 除算をする関数
 * 除数が 0 のとき、例外を発生させる
 *
 * @param a 被除数
 * @param b 除数
 * @return 除算の結果（整数）
 */
fun divide(a: Int, b: Int): Int {
    if (b == 0) {
        throw IllegalArgumentException("除数を 0 にしてはいけません")
    }
    return a / b
}
