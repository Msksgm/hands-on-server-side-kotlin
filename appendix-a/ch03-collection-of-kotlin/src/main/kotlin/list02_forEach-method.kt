fun main(args: Array<String>) {

    // list06_repetition-collection.kt
    mapOf(1 to "first", 2 to "second").map { "${it.key}-${it.value}" }.forEach(::println)

    val number = listOf("one", "two", "three", "four")
    number.filter {
        it.length > 3
    }.map {
        "$it は3文字より大きいです"
    }.forEach {
        println(it)
    }
}
