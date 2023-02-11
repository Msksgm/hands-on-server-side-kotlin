fun main(args: Array<String>) {

    // mapOf
    println("=== mapOf ===")
    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
    println(numbersMap)

    println(numbersMap.get("key1"))
    println(numbersMap.get("key0"))

    // mutableMapOf
    println("=== mutableMapOf ===")
    val mNumbersMap = mutableMapOf("one" to 1, "two" to 2)
    println(mNumbersMap)
    mNumbersMap.put("three", 3)
    println(mNumbersMap)
    mNumbersMap["one"] = 11
    println(mNumbersMap)
}
