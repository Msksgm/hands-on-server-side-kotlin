package ch01

fun main(args: Array<String>) {
    val name: String
    // 以下の if 文は必ずどちらかのブロックで 1 回だけ初期化されていることが保証されているため、コンパイルが通る
    if (isRandom()) {
        name = "Alice"
    } else {
        name = "Bob"
    }
    println(name)
}

/**
 * isRandom()
 *
 * Math.random() は 0 <= n < 1 範囲でランダムな数字（Double）を生成する
 * 二分の一の確率で true/false が決まる
 */
fun isRandom(): Boolean = Math.random() >= 0.5
