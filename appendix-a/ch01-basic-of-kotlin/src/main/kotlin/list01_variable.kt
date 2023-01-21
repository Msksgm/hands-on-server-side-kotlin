fun main(args: Array<String>) {
    // val で宣言すると不変
    val id = 100
    println(id) // 100
    // 再代入しようとすると、コンパイルエラーが発生する
    // id = 200

    // var で宣言すると可変
    var name = "Alice"
    println(name) // Alice
    name = "Bob"
    println(name) // Bob
}
