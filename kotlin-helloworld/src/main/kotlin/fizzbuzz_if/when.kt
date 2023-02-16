package fizzbuzz_when

fun fizzBuzz1(i: Int): String {
    val result: String
    when {
        i % 15 == 0 -> {
            result = "FizzBuzz"
        }

        i % 5 == 0 -> {
            result = "Fizz"
        }

        i % 3 == 0 -> {
            result = "Buzz"
        }

        else -> {
            result = "%i"
        }
    }
    return result
}

fun fizzBuzz2(i: Int): String {
    return when {
        i % 15 == 0 -> {
            "FizzBuzz"
        }

        i % 5 == 0 -> {
            "Fizz"
        }

        i % 3 == 0 -> {
            "Buzz"
        }

        else -> {
            "%i"
        }
    }
}

fun fizzBuzz3(i: Int): String {
    return when {
        i % 15 == 0 -> {
            "FizzBuzz"
        }

        i % 5 == 0 -> {
            "Fizz"
        }

        i % 3 == 0 -> {
            "Buzz"
        }

        else -> {
            "%i"
        }
    }
}

fun main(args: Array<String>) {
    println(fizzBuzz1(15))
    println(fizzBuzz2(5))
    println(fizzBuzz3(3))
}
