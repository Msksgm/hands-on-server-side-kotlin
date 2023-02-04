fun main(args: Array<String>) {
    // while の基本
    println("=== while ===")
    var j: Int = 0
    while (j < 5) {
        println(fizzBuzz(j))
        j++
    }

    // do while
    println("=== do while ===")
    var k: Int = 6
    do {
        println(fizzBuzz(j))
        k++
    } while (k < 5)
}

// fun fizzBuzz(i: Int) = when {
//    i % 15 == 0 -> "FizzBuzz"
//    i % 5 == 0 -> "Buzz"
//    i % 3 == 0 -> "Fizz"
//    else -> "$i"
// }
