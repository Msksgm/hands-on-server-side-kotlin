fun main(args: Array<String>) {

    // forEach
    println("=== forEach ===")
    listOf(1, 2, 3).forEach { println(it * 2) }
    val l: List<String> = listOf("a", "b", "c")
    l.forEach {
        when (it == "b") {
            true -> println("bb")
            false -> println(it)
        }
    }

    // map: 各要素を別の形に変換した List を作成
    println("=== map ===")
    listOf(4, 5, 6).map { print("${it * 2},") }
    println()
    mapOf(1 to "first", 2 to "second").map { "${it.key}-${it.value}" }.forEach(::println)
}
