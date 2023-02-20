fun main(args: Array<String>) {

    // listOf
    println("=== listOf ===")
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.get(2))
    println(numbers[2])
    println(numbers.size)
    println(numbers.isEmpty())

    // mutableListOf
    println("=== mutableListOf ===")
    val mNumbers = mutableListOf(1, 2, 3, 4)
    mNumbers.add(5)
    println(mNumbers)
    mNumbers.removeAt(1)
    println(mNumbers)
    mNumbers[0] = 0
    println(mNumbers)
}
