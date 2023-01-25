package list05_when

fun main(args: Array<String>) {
    whenNums(10)
    whenNums2(20)
    whenNums3(30)
    // when: ブロック
    println(maxUsingWhen(1, 2))
    // when: 式
    println(maxUsingWhenExp(1, 2))
}

fun whenNums(num: Int) {
    when (num) {
        10 -> {
            println("num is 10")
        }

        20 -> {
            println("num is 20")
        }

        30 -> {
            println("num is 30")
        }

        else -> {
            println("num is Unknown Number")
        }
    }
}

fun whenNums2(num: Int) {
    when {
        num < 10 -> {
            println("num is less than 10")
        }
        num == 10 -> {
            println("num is 10")
        }
        else -> {
            println("num is greater than 10")
        }
    }
}

fun whenNums3(num: Int) {
    when {
        num < 10 -> {
            println("num is less than 10")
        }
        num == 10 -> {
            println("num is 10")
        }
        num > 10 -> {
            println("num is greater than 10")
        }
    }
}

// when を文として扱う
fun maxUsingWhen(a: Int, b: Int): Int {
    when (a > b) {
        true -> {
            return a
        }

        false -> {
            return b
        }

        else -> {
            return a
        }
    }
}

// when を式で扱う
fun maxUsingWhenExp(a: Int, b: Int): Int {
    val num = when (a > b) {
        true -> {
            a
        }

        false -> {
            b
        }

    }
    return num
}
