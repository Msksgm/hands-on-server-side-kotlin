fun main(args: Array<String>) {
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.first())
    println(numbers.last())
    val firstLongerThan3 = numbers.first { it.length > 3 }
//    val firstLongerThan10 = numbers.first{it.length > 10}
    val firstLongerThan10 = numbers.firstOrNull { it.length > 10 }
    println(firstLongerThan3)
    println(firstLongerThan10)

    val emptyNumber: List<String> = listOf()
//    println(emptyNumber.first())
    println(emptyNumber.firstOrNull())

}
