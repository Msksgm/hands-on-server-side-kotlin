package fizzbuzz_if

fun fizzBuzz1(i: Int): String {
    if (i % 15 == 0) {
        return "FizzBuzz"
    } else if (i % 5 == 0) {
        return "Fizz"
    } else if (i % 3 == 0) {
        return "Buzz"
    } else {
        return "$i"
    }
}

fun fizzBuzz2(i: Int): String {
    val result = if (i % 15 == 0) {
        "FizzBuzz"
    } else if (i % 5 == 0) {
        "Fizz"
    } else if (i % 3 == 0) {
        "Buzz"
    } else {
        "$i"
    }
    return result
}

fun fizzBuzz3(i: Int): String {
    return if (i % 15 == 0) {
        "FizzBuzz"
    } else if (i % 5 == 0) {
        "Fizz"
    } else if (i % 3 == 0) {
        "Buzz"
    } else {
        "$i"
    }
}

fun main(args: Array<String>) {
    println(fizzBuzz1(15))
    println(fizzBuzz2(5))
    println(fizzBuzz3(3))
    println(fizzBuzz3(1))
}
