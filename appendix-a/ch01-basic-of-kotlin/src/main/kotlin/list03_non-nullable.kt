fun main(args: Array<String>) {
    val fuga: String? = null
    val piyo: String = "piyo"
    fun hoge(s: String): String {
        return s
    }
}

//fun printNullableStringLength(s: String?) {
//    println(s.length) // s が null の可能性がないので、コンパイルエラーが発生しない
//}

fun printStringLength(s: String) {
    println(s.length) // s が null の可能性がないので、コンパイルエラーが発生しない
}

fun printStringLengthIf(s: String?) {
    if (s.isNullOrBlank()) {
        println(0)
    } else {
        println(s.length) // s が null の可能性がないので、コンパイルエラーが発生しない
    }
}

fun printStringLengthSafe(s: String?) {
    // s が null でない場合、文字列の長さを返す。null の場合 0 を返す
    println(s?.length ?: 0)
}
