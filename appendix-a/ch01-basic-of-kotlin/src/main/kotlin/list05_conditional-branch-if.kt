package list05_if

fun main(args: Array<String>) {
    isTen(10)
    isTen2(20)

    max(10, 20)
}

fun isTen(num: Int) {
    if (num == 10) {
        println("num is 10")
    }
}

fun isTen2(num: Int) {
    if (num == 10) {
        println("num is 10")
    } else if (num < 10) {
        println("num is less than 10")
    } else {
        println("num is greater than 10")
    }
}

fun max(a: Int, b: Int): Int {
    val num =
        if (a > b) {
            a
        } else if (b > a) {
            b
        } else if (a == b) {
            a
        } else {
            a
        }
    return num
}

//以下はコンパイルが通らない
//fun maxFail(a: Int, b: Int): Int {
//    val num =
//        if (a > b) {
//            a
//        } else if (b > a) {
//            b
//        } else if (a == b) {
//            a
//        }
//    return num
//}
