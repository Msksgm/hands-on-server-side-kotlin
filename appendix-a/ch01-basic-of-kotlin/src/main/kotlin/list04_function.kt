fun main(args: Array<String>) {
    // 関数呼び出し
    println(greeting("Alice"))

    // 式本体を持つ関数の呼び出し
    println(greetingExp("Bob"))

    println(greetingExp2("Carol"))
}

// 関数宣言は fun 関数名(引数): 戻り値の型
fun greeting(name: String): String {
    return "Hello ${name}!"
}

// 式本体を持つ関数
fun greetingExp(name: String): String = "Hello ${name}!"

// 戻り値を省略可能
fun greetingExp2(name: String) = "Hello ${name}!"
