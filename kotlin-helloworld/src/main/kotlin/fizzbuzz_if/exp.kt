package exp

enum class Division {
    DIVISIBLE_BOTH_THREE_AND_FIVE,
    DIVISIBLE_THREE_AND_NOT_DIVISIBLE_FIVE,
    NOT_DIVISIBLE_THREE_AND_DIVISIBLE_FIVE,
    NOT_DIVISIBLE_THREE_AND_FIVE,
}


fun a() {
    val x = 100
    when (x) {
        0 -> println("0")
    }
}

// 式として使うとエラー(elseがない)が発生する(一応網羅性の担保)
//fun b() {
//    val x = 100
//    val y = when (x) {
//        0 -> 0
//    }
//}

fun c() {
    val x = Division.DIVISIBLE_BOTH_THREE_AND_FIVE
    when (x) {
        Division.DIVISIBLE_BOTH_THREE_AND_FIVE -> println("FizzBuzz")
        Division.NOT_DIVISIBLE_THREE_AND_DIVISIBLE_FIVE -> println("Fizz")
        Division.DIVISIBLE_THREE_AND_NOT_DIVISIBLE_FIVE -> println("Buzz")
        Division.NOT_DIVISIBLE_THREE_AND_FIVE -> println(x)
    }
}


fun main() {
    a()
    c()
}